@DealerEarningStatement
Feature: Dealer Earning Statement Screen

  Background:
    Given User is on WSL Login DSS
    When User login into application with "sverma7" and "Mazdausa@0000"

  Scenario Outline: Dealer earning screen loading
    Then Click on the DealerEarningStatement from the menu
    And Verify the Heading of the screen
    Examples:
      | username | password       |
      | sverma7  | Mazdausa@9999  |

  Scenario Outline:Checking Statement column count
    Then Click on the DealerEarningStatement from the menu
    Then Search "<dealercd>"
    And Select date-range from "<startMon>" to "<endMon>" and submit
    Then verify the table column names
    Examples:
      | username | password       | dealercd | startMon | endMon |
      | sverma7  | Mazdausa@9999  | 24000    |  03      | 06     |
