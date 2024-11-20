@EarningSummeryScreen
Feature: Earning Summery

  Background:
    Given User is on WSL Login DSS
    When User login into application with "sverma7" and "Mazdausa@0000"

  Scenario Outline: Earning Summery Checking
    Then Verifying the Earning Summery page is loading properly
    Then enter dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    And Verify there have 13 columns in the table.
    When Click on the Earning_details
    Then The Earning details Page should open
    And Verify that Forfeited_Escrow_$ row added below the Brand Commitment_(payment + Escrow)
    And click on the Return Button
    Then the page should redirect to Earning Summery Screen

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |
      | sverma7       | Mazdausa@0000  |  24000   |  05     |   2024  |



