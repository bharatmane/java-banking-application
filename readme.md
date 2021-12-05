# Problem Statement:

Indian Bank is in operation since 1900. All the work and transactions are recorded manually. Bank wants to get advanced and wants to use technology in order to provide users self-transactions services. In Version 1 of their Banking Software, the bank wants to give support to the following

## Functionalities:

1.  Credentials verification (BankAccountNo, Password)
2.  Deposit
3.  Withdrawal
4.  Transfer
5.  OTP generation
6.  All Transactions being recorded in a text file.

You are required to create a program where all the above-mentioned functionalities are operational.

## Instructions

1.  Customers will have only two fields in the first version of the application bankAccountNo, password. All the values can be hardcoded and stored in a list of type Customer.
    
2.  The system will ask the user to enter the credentials, if credentials match, the code will display 4 options
    1.  Deposit
    2.  Withdraw
    3.  Transfer
    4.  Logout
3.  Upon entering the option, the user should be able to perform the operations and all the operations should be recorded in the transaction file.
    
4.  For Transfer, the system will generate an OTP of 4 digits. Before transferring money, the user should verify the OTP.
    
5.  Amount verification(withdrawal amount<balance) conditions can be skipped in the code.
    

## Additional features that can be added

1.  OTP is sent to mobile no. instead of displaying on the console. You can use open Sms APIs like Twilio, or Fast2SMS
2.  Java 8 features such as Lambda functions can be used instead of writing boilerplate code.
3.  Different features like mini statement can be added which will read the last five transactions from the txt file and will display them on the console.
4.  Validations like accountBalance > withdrawal amount/ transfer amount can also be added.
5.  You can display the exact amount left after a transaction is done.
