@BaseQualifier
Feature: Base Section

  Scenario Outline: Base Qualifier Checking
    Given User is on WSL Login DSS
    When User login into application with "<username>" and "<password>"
    Then Verify that MBEP_2016_Q3_to_2018_Q2 Page is opening correctly.
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then check the "<BaseQualHeading>" and "<BaseQualDescrip>"
    And Collapse functionality is working properly.
    Then Open the FSL section
    And Verify the "<FSLheading>" and "<FSLDescrip>"
    And Verify the "<FSLelements>" present in the FSL
    Then Verify the DataAgreement as "<DataAgreementHeading>" and "<DataAgreeMentDescrip>"

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   BaseQualHeading   |   BaseQualDescrip                                                                         |   FSLheading       |   FSLDescrip                                                                                                |   DataAgreementHeading   |   DataAgreeMentDescrip                                  |   FSLelements                                                                                                                                                                       |
      | sverma7       | Mazdausa@9999  |  24000   |  03     |   2024  |   BASE QUALIFIERS   |   All base qualifiers must be achieved to meet base requirements for program eligibilty   |   FACILITY AS OF   |   Must pass applicable annual facility image & type inspections; based on data as of 25th of prior month    |   DATA AGREEMENT AS OF   |   Must be enrolled by end of preceding calendar month   |   CURRENT FACILITY TYPE,CURRENT FACILITY IMAGE,FACILITY TYPE INSPECTION PASSED,IMAGE INSPECTION PASSED,FACILITY TYPE COMMITTED,FACILITY IMAGE COMMITTED,IN ESCROW,CURRENT STATUS    |

  Scenario Outline: Base-Qualifier is not achieved.
    Given User is on WSL Login DSS
    When User login into application with "<username>" and "<password>"
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    When Base-Qualifer is not achieved.
#    Then Brand-Section will show "<Status>".
    And L&RSection will show "<Status>"
    And Digitization will show "<Status>"


    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   Status                             |
      | sverma7       | Mazdausa@9999  |  10131   |  10     |   2025  |   MBEP BASE QUALIFIER\nNOT ACHIEVED  |