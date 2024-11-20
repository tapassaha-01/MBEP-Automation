@EarningDetailsScreen
Feature: Earning details

  Background:
    Given User is on WSL Login DSS
    When User login into application with "sverma7" and "Mazdausa@0000"

  Scenario Outline: Earning Summery calculation Checking
    Then navigate to the Earning Details Screen
    And search with a "<dealer_code>" and submit
    Then calculate the total value for the "<mon>"
    Examples:
      | username | password       | dealer_code | mon |
      | sverma7  | Mazdausa@0000  |  24000       | MAR |
