Feature: Battleships tests http://www.techhuddle.com/tests/battleships/v4test/index.php

  Background: Page is loaded
    Given user is on BattleShips Page


  Scenario: Hit coordinate
    When I enter coordinate:"A5"
    And I press Submit button
    Then message should be correct
    And enter form should contain text "Enter coordinates (row, col), e.g. A5"


    @dev
  Scenario: Hit coordinate
    When I hit multiple coordinates:
      | A1 | A2 | A3 | A4 | A5 | A6 | A7 | A8 | A9 |
      | B1 | B2 | B3 | B4 | B5 | B6 | B7 | B8 | B9 |
      | C1 | C2 | C3 | C4 | C5 | C6 | C7 | C8 | C9 |
      | D1 | D2 | D3 | D4 | D5 | D6 | D7 | D8 | D9 |
      | E1 | E2 | E3 | E4 | E5 | E6 | E7 | E8 | E9 |
      | F1 | C2 | C3 | C4 | C5 | C6 | C7 | C8 | C9 |


  Scenario: Show battleships current status
    When I enter coordinate:"show"
    And I press Submit button
    Then ships count should be:13
    And miss count should be:0


  Scenario: Reset battleships game
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships count should be:0
    And miss count should be:0


  Scenario Outline: Test multiple resets and shows
    When I enter coordinate:"<command>"
    And I press Submit button
    Then ships count should be:<ships>
    And miss count should be:<miss>
    Examples:
      | command | ships | miss |
      | reset   | 0     | 0    |
      | reset   | 0     | 0    |
      | reset   | 0     | 0    |
      | show    | 13    | 0    |
      | show    | 13    | 0    |
      | show    | 13    | 0    |
      | show    | 13    | 0    |
      | reset   | 0     | 0    |
      | reset   | 0     | 0    |
      | reset   | 0     | 0    |
      | show    | 13    | 0    |
      | show    | 13    | 0    |


