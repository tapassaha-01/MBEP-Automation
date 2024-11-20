@GenericObject
Feature: Generic Object Page
  Background:
    Given User is on WSL Login DSS
    When User login into application with "sverma7" and "Mazdausa@0000"
    Then navigate to the Generic Objective Screen

  Scenario Outline: Checking the Heading and Upload Options
    Then Verify the "<Page_Heading>"
    And Verify element present in the screen for the "<dealerTyp>"
    Then Verify all MSPU sections should be shown N/A for MBEP-NC dealer
    And Check the alert for saving record with empty fields
    Examples:
      | Page_Heading            | dealerTyp |
      | Generic Objective Upload| MBEP-NC   |

