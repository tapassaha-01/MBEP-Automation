@L&RSection
Feature: L&R Section

Scenario Outline: MCVP checking
  Given User is on WSL Login DSS
  When User login into application with "<username>" and "<password>"
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


    Scenario Outline: MSS and MSPU checking
    Given User is on WSL Login DSS
    When User login into application with "<username>" and "<password>"
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then verify that "<MSSHeading>" coming under L&R Pre-Qualifier.
    And "<MSPUHeading>" is coming under L&R Earning Elements.
    Then Percentile value should match with the Obj min value.
    And verify the MSPU link "<MSPULink>".
    Then Verify the "<FSLHeading>" and "<FSLSubHeading>".
    And FSL Percentile value should match with Obj min value.

    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   MSSHeading                    |   MSPUHeading                       |   MSPULink                                            |   FSLHeading                    |   FSLSubHeading                                                   |
      | sverma7       | Mazdausa@9999  |  24000   |   09    |  2024   |   MAZDA SERVICE SCHEDULER AS OF |   MAINTENANCE SERVICE PER UIO AS OF |   https://portal.mazdausa.com/MNAOServiceView.html    |   FIRST SERVICE LOYALTY AS OF   |   Must achieve 25th percentile and 30% FSL performance minimum    |

    Scenario Outline: L&R Section Pre-Qualifier not achieved.
    Scenario Outline: Training is not achieved.
