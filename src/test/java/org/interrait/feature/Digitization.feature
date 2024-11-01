@Digitization
Feature: Digitization Section

  Scenario Outline: Pre-Qualifier UI checking
    Given User is on WSL Login DSS
    When User login into application with "<username>" and "<password>"
    Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
    Then verify the "<CVAP>" and "<CX360>" heading text.
    Then the CX360 section loaded properly.


    Examples:
      | username      | password       |  dlrcd   |  Month  |   Year  |   CVAP                                              |   CX360                       |   cx360link                                 |
      | sverma7       | Mazdausa@9999  |  24000   |  03     |   2024  |   CONNECTED VEHICLE ACTIVATION PERFORMANCE AS OF    |   CX360 RECORD HEALTH AS OF   |   https://portal.mazdausa.com/CX360.html    |

    Scenario Outline: Earning Elements Checking
      Given User is on WSL Login DSS
      When User login into application with "<username>" and "<password>"
      Then select dlrcd as "<dlrcd>", mon "<Month>", year "<Year>" and click on submit
      Then Verify the "<Earning_ele>" present in that section for the "<dealerTyp>"
      And Load the Digital Showroom section

      Examples:
        | username      | password       |  dlrcd   |   Month   |   Year  |   Earning_ele                                                                                                                   |   dealerTyp   |
        | sverma7       | Mazdausa@9999  |  23850   |    10     |   2024  |   MAZDA DIGITAL SHOWROOM (MDS) QUALIFIER AS OF,DIGITAL SERVICE UTILIZATION AS OF                                                |   MBEP-NC     |
        | sverma7       | Mazdausa@9999  |  10131   |    11     |   2024  |   MAZDA DIGITAL SHOWROOM (MDS) ENROLLMENT AS OF,MAZDA DIGITAL SHOWROOM (MDS) QUALIFIER AS OF,DIGITAL SERVICE UTILIZATION AS OF  |   MBEP/HI     |