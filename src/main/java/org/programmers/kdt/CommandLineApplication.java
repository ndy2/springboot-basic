package org.programmers.kdt;

import org.programmers.kdt.command.Command;
import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;
import org.programmers.kdt.utils.DigitUtils;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication implements Runnable {
    private final Input input;
    private final Output output;

    private final String requestCommandMeesage
            = MessageFormat.format(
                    """
                    
                    === Voucher Program ===
                    [Type {0} to exit the program]
                    Type {1} to create a voucher.
                    Type {2} to list all voucher.
                    Type {3} to enter customer managing page.
                    """,
            Command.EXIT, Command.CREATE, Command.LIST, Command.CUSTOMER);
    private final String requestVoucherTypeMessage
            = MessageFormat.format("Choose voucher type : {0}", List.of(VoucherType.values()));

    @Autowired
    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        boolean termi = false;
        while (!termi) {
            Command command;

            command = Command.getCommandFromInput(requestCommandMeesage, Command.getVoucherApplicationCommand(), input, output);
            if (command == null) continue;

            switch (command) {
                case CREATE -> {
                    VoucherType voucherType;

                    voucherType = getVoucherType();
                    if (voucherType == null) continue;

                    String discount = input.input("How much of a discount do you want to give?");
                    while (!DigitUtils.isDigits(discount)) {
                        output.inputError("Invalid Input. Only digits are allowed.");
                        discount = input.input("How much of a discount do you want to give?");
                    }

                    Voucher voucher;
                    try {
                        voucher = voucherService.createVoucher(voucherType, UUID.randomUUID(), Long.parseLong(discount));
                    } catch (RuntimeException e) {
                        output.inputError(e.getMessage());
                        continue;
                    }
                    output.printSuccessMessage();
                    output.print(voucherService.getPrintFormat(voucher));
                }
                case LIST -> {
                    List<Voucher> allVoucher = voucherService.getAllVouchers();
                    for (Voucher voucher : allVoucher) {
                        output.print(voucherService.getPrintFormat(voucher));
                    }
                }
                case EXIT -> {
                    output.sayGoodBye();
                    termi = true;
                }
                case CUSTOMER -> {
                    // Enter Customer-Voucher Managing Application
                    CustomerVoucherManagingApplication customerVoucherManagingApplication = applicationContext.getBean(CustomerVoucherManagingApplication.class);
                    customerVoucherManagingApplication.run();
                }
            }
        }
    }

    private VoucherType getVoucherType() {
        VoucherType voucherType;
        try {
            voucherType = VoucherType.of(input.input(requestVoucherTypeMessage));
        } catch (RuntimeException e) {
            output.inputError(MessageFormat.format("Invalid Input. You can create {0} discount voucher", List.of(VoucherType.values())));
            return null;
        }
        return voucherType;
    }
}
