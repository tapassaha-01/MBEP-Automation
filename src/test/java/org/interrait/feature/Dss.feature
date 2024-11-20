@DSSPage
Feature: DSS Page

  Background:
    Given User is on WSL Login DSS
    When User login into application with "sverma7" and "Mazdausa@0000"

  @BaseQualifier
  Scenario Outline: Base Qualifier Checking
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
  @BaseQualifier
  Scenario Outline: Base-Qualifier is not achieved.

    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    When Base-Qualifer is not achieved.
#    Then Brand-Section will show "<Status>".
    And L&RSection will show "<Status>"
    And Digitization will show "<Status>"

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   Status                             |
      | sverma7       | Mazdausa@9999  |  10131   |  10     |   2025  |   MBEP BASE QUALIFIER\nNOT ACHIEVED  |

  @BrandSection
  Scenario Outline: Brand Section Checking
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then Verify the title and description of each element inside brand section.
    And Check the collapse button will work properly.
    Then Verify the "<note>" present inside the dealer financial statement.

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   note                                                                                                                                 |
      | sverma7       | Mazdausa@9999  |  24000   |  03     |   2024  |   **Current month qualification for Dealer Financial Statement is based on timely submission of financial statement two months prior   |

  @Digitization
  Scenario Outline: Pre-Qualifier UI checking
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then verify the "<CVAP>" and "<CX360>" heading text.
    Then the CX360 section loaded properly.

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   CVAP                                              |   CX360                       |   cx360link                                 |
      | sverma7       | Mazdausa@9999  |  24000   |  03     |   2024  |   CONNECTED VEHICLE ACTIVATION PERFORMANCE AS OF    |   CX360 RECORD HEALTH AS OF   |   https://portal.mazdausa.com/CX360.html    |
  @Digitization
  Scenario Outline: Earning Elements Checking
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then Verify the "<Earning_ele>" present in that section for the "<dealerTyp>"
    And Load the Digital Showroom section
    And Sales Performance Section should not come for MBEP dealer

    Examples:
      | username      | password       |  dlrcd   |   Month   |   Year  |   Earning_ele                                                                                                                   |   dealerTyp   |
      | sverma7       | Mazdausa@9999  |  23850   |    10     |   2024  |   MAZDA DIGITAL SHOWROOM (MDS) QUALIFIER AS OF,DIGITAL SERVICE UTILIZATION AS OF                                                |   MBEP-NC     |
      | sverma7       | Mazdausa@9999  |  10131   |    11     |   2024  |   MAZDA DIGITAL SHOWROOM (MDS) ENROLLMENT AS OF,MAZDA DIGITAL SHOWROOM (MDS) QUALIFIER AS OF,DIGITAL SERVICE UTILIZATION AS OF  |   MBEP/HI     |
  @L&RSection
  Scenario Outline: MCVP checking
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then Verify the L&R section Heading "<L&RSectionHeading>"
    Then Open the MCVP section
    When the sectional heading as "<MCVPHeading>" with the Date "MM/DD/YYYY"
    Then Verify the MCVP subheading contains "<MCVPSubHeading>" sentence.
    And Verify the earning month and next month status.
    Then Verify that MCVPDataTable contains Previous "3" + current month of data
    Then Check the calculation for "<McvpTableRow>" values with the current and previous month value.
    Then Verify the MCVP website link is redirecting to "<MCVPLink>"


    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   McvpTableRow                          |   L&RSectionHeading         |   MCVPHeading                          |    MCVPSubHeading                                                                                  |   MCVPLink                                                                              |
      | sverma7       | Mazdausa@9999  |  24000   |  03     |   2024  |   In Stock Minimum Requirement          |   LOYALTY & RETENTION (L&R) |   MAZDA COURTESY VEHICLE PROGRAM AS OF |    Must maintain minimum # of units for minimum 80% of days in standardized rolling 90-day period  |   https://portal.mazdausa.com/dealershome/service_parts/mazda_courtesy_vehicle_program/ |

  @L&RSection
  Scenario Outline: MSS and MSPU checking
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then verify that "<MSSHeading>" coming under L&R Pre-Qualifier for the "<Month>"
    And "<MSPUHeading>" is coming under L&R Earning Elements
    Then Percentile value should match with the Obj min value.
    And verify the MSPU link "<MSPULink>".
    Then Verify the "<FSLHeading>" and "<FSLSubHeading>".
    And FSL Percentile value should match with Obj min value.

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   MSSHeading                    |   MSPUHeading                       |   MSPULink                                            |   FSLHeading                    |   FSLSubHeading                                                   |
      | sverma7       | Mazdausa@9999  |  24000   |   09    |  2024   |   MAZDA SERVICE SCHEDULER AS OF |   MAINTENANCE SERVICE PER UIO AS OF |   https://portal.mazdausa.com/MNAOServiceView.html    |   FIRST SERVICE LOYALTY AS OF   |   Must achieve 25th percentile and 30% FSL performance minimum    |
  @L&RSection
  Scenario Outline: L&R Section Pre-Qualifier not achieved
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    When Any of L&RSection Pre-Qualifier is not achieved for Earning Month
    Then Both the Earning Elements should be not achieved for Earning Month
    Examples:
      | username      | password       | dlrcd    | Month   | Year    |
      | sverma7       | Mazdausa@9999  |  24000   |   05    |  2024   |
  @L&RSection
  Scenario Outline: MBEP_2024 Tracking section checking
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then MBEP_2024 New Element Tracking should be visible
    And MSPU Earning Element should be present inside it
    Examples:
      | username      | password       | dlrcd    | Month   | Year    |
      | sverma7       | Mazdausa@9999  |  24000   |   05    |  2024   |
  @L&RSection
  Scenario Outline: Training is not achieved
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    When Traning is Not achieved for Earning Month for "<dealerTyp>"
    Then MSS Earning Element should be Not achieved for Earning Month
    Then Digitization Section Earning Elements should be Not achieved for Earning Month

    Examples:
      | username      | password       | dlrcd    |   Month | Year    |  dealerTyp |
      | sverma7       | Mazdausa@9999  |  24020   |   11    |  2024   |  MBEP-NC   |
  @L&RSection
  Scenario Outline: Checking MBEP-NC dealer elements
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then FSL section should not come for MBEP-NC dealer
    And Sales performance Section should come for MBEP-NC dealer
    Examples:
      | username | password       | dlrcd    | Month   | Year |
      | sverma7  | Mazdausa@9999  |  24020   |   11    |  2024|