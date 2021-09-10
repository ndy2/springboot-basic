package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Console implements Input, Output {
    private final static Logger logger = LoggerFactory.getLogger(Console.class);
    private final Scanner input;

    public Console() {
        this.input = new Scanner(System.in);
    }

    @Override
    public void printStartAppInfo() {
        System.out.println("=== Voucher Program ===");
    }

    @Override
    public void printCommandList() {
        System.out.println("""
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type customers to list all customers
                Type blacks to list all blacklist customers""");
    }

    @Override
    public void printCommandError(String command) {
        String message = """
                [ERROR]Invalid command type 
                Your input : {0}
                ===========""";
        System.out.println(MessageFormat.format(message, command));
    }

    @Override
    public void printCreateTypes() {
        System.out.print("""
                <vouhcerType> <data>
                voucherType
                \tP : PercentDiscountVoucher(1~100)
                \tF : FixedAmountVoucher(1~100000)
                data is long value
                ex) P 10
                input voucher info:""");
    }

    @Override
    public <T> void printList(List<T> list) {
        list.forEach(System.out::println);
    }

    @Override
    public CommandType getInputCommand() {
        printCommandList();
        System.out.print("input command:");
        String command = input.nextLine();
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error(MessageFormat.format("Invalid command type. Your input -> {0}", command));
            printCommandError(command);
            return CommandType.ERROR;
        }
    }

    @Override
    public String getCreateLine() {
        return input.nextLine();
    }
}