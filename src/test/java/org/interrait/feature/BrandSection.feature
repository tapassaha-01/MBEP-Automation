@BrandSection
Feature: Base Section

  Scenario Outline: Brand Section Checking
    Given User is on WSL Login DSS
    When User login into application with "<username>" and "<password>"
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then Verify the title and description of each element inside brand section.
    And Check the collapse button will work properly.
    Then Verify the "<note>" present inside the dealer financial statement.

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   note                                                                                                                                 |
      | sverma7       | Mazdausa@999  |  24000   |  03     |   2024  |   **Current month qualification for Dealer Financial Statement is based on timely submission of financial statement two months prior   |