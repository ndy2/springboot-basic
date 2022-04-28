package org.prgrms.deukyun.voucherapp.view.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleServiceTest {

    ByteArrayOutputStream out;
    ConsoleService console = new ConsoleService();
    String ANSI_YELLOW = "\u001B[33m";

    @BeforeEach
    void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    void 성공_출력_메시지() {
        //setup
        String msg = "Write Me!";

        //action
        console.write(msg);

        //assert
        assertThat(out.toString()).contains(ANSI_YELLOW);
        assertThat(out.toString()).contains("Write Me!");
    }

    @Test
    void 성공_출력_메시지_와_인자() {
        //setup
        String msg = "Write Me! %s";
        String[] args = {"Me Either!"};

        //action
        console.write(msg, args);

        //assert
        assertThat(out.toString()).contains("Write Me! Me Either!");
    }

    @Test
    void 성공_입력_인풋스트립() {
        //setup
        String input = "Read Me!";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        console = new ConsoleService();

        //action
        String readMessage = console.readLine();

        //assert
        assertThat(readMessage).isEqualTo(input);
    }
}