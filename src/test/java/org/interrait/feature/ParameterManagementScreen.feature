@ParameterManagement
  Feature: Parameter Management Page
    Background:
      Given User is on WSL Login DSS
      When User login into application with "sverma7" and "Mazdausa@0000"
      Then navigate to the parameter management screen

    Scenario Outline: Verifying MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section
      And Verify the Heading is coming as "<heading>"
      And Verify the elements present in MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section for the "<mon>" and "<quater>" and "<year>"
      Examples:
        | username | password       | mon | quater  | year  | heading             |
        | sverma7  | Mazdausa@9999  | 07  | 3       | 2024  | Parameter Management|
        | sverma7  | Mazdausa@9999  | 06  | 2       | 2024  | Parameter Management|

      Scenario Outline: Verifying the elements present in the screen
        Then Verify the elements present inside MBEP-HI Set Payout Section
        And Verify the elements present inside MBEP SET PAYOUT PERCENTAGE
        And Verify the elements present inside MBEP-NC Set Payout Section
        And Verify the elements present inside MBEP-NC/MSEP Set sales payout Section
        And Verify the elements present inside Set Processing Parameters
        Examples:
          | username | password       |
          | sverma7  | Mazdausa@9999  |
#
    Scenario: Checking the value setting functionality
      Then Set the "1.00,1.99,2.00,1.33" for "MBEP SET PAYOUT PERCENTAGE section"
      Then Verify the updated values with "1.00,1.99,2.00,1.33"
      Then Set the "1.00,1.99,2.00" for "MBEP-HI SET PAYOUT PERCENTAGE"
      Then Verify the updated values with "1.00,1.99,2.00"
      Then Set the "20000,1200,2000" for "MBEP-NC SET PAYOUT"
      Then Verify the updated values with "20000,1200,2000"

    Scenario Outline: Checking alert for  MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section
      Then Select "<year>", "<quater>" and "<mon>"
      And Click on Retrieve button
      Then fill "<value>" to the "<input_field>" in MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section
      Then observe the "<alert_massage>"

      Examples:
        | year | quater | mon | value | input_field  | alert_massage                            |
        | 2024 |  3     | 08  | 0.0222| MSPU Payout %| Value length should not be greater than 4|
        | 2024 |  3     | 08  | 10    | MSPU Payout %| Value range allowed- 0.00 to 9.99        |

    Scenario Outline: Checking alert for MBEP NC SET PAYOUT PERCENTAGE section
      Then Select "<year>", "<quater>" and "<mon>"
      And Click on Retrieve button
      Then fill value "<value>" to the "<input_field>" in the  MBEP NC SET PAYOUT PERCENTAGE section
      And observe the "<alert_massage>"
      Examples:
        | year | quater | mon | value   | input_field             | alert_massage                              |
        | 2024 |  1     | 01  | 77777777| Brand Payout Amount- RE | Value length should not be greater than 7  |







