package io.github.bharatmane.banking;


import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



class PrompterTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    @BeforeEach
    public void init() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    @Test
    @DisplayName("should prompt welcome message")
    void shouldPromptWelcomeMessage(){
        String expectedGreetMessage = "Welcome to Indian Banking Services!";
        Scanner scanner = new Scanner(System.in);

        //Given

        Prompter prompter = new Prompter(printStream, scanner);
        //When
        prompter.greetUser();

        //Then
        assertThat(outputStream.toString()).contains(expectedGreetMessage);
    }

    @Test
    @DisplayName("should prompt login options")
    void shouldPromptLoginOptions(){
        Scanner scanner = new Scanner("1\n");
        String expectedLoginString = "1. Login";
        String expectedExitString = "2. Exit";
        //Given
        Prompter prompter = new Prompter(printStream, scanner);
        //When
        int chosenOption = prompter.promptLogin();

        //Then
        assertThat(outputStream.toString()).contains(expectedLoginString);
        assertThat(outputStream.toString()).contains(expectedExitString);
        assertThat(chosenOption).isEqualTo(1);
    }

    @Test
    @DisplayName("should login when chosen as option 1")
    void shouldLoginWhenChosenOption1(){
        Scanner scanner = new Scanner("1\njack");

        //Given
        Prompter prompter = new Prompter(printStream,scanner);
        //When
        prompter.promptLogin();
        prompter.promptAccountNo();

        //Then
        assertThat(prompter.currentOption()).isEqualTo("jack");
    }


//    @Test
//    @DisplayName("should ask login credentials for option 1")
//    void shouldLoginWhenCorrectCredentialsGiven(){
//        Scanner scanner = new Scanner("1\njack\njack@123");
//
//        //Given
//        Prompter prompter = new Prompter(printStream,scanner);
//        //When
//        prompter.login();
//
//        //Then
//        assertThat(outputStream.toString()).contains("Please Enter User Name");
//    }



}
