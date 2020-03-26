Feature: Battleships tests http://www.techhuddle.com/tests/battleships/v4test/index.php

  Background: Page is loaded
    Given user is on BattleShips Page

  Scenario: Hit coordinate
    When I hit multiple coordinates:
      | A1 | A2 | A3 | A4 | A5 | A6 | A7 | A8 | A9 | A10 |
      | B1 | B2 | B3 | B4 | B5 | B6 | B7 | B8 | B9 | B10 |
      | C1 | C2 | C3 | C4 | C5 | C6 | C7 | C8 | C9 | C10 |
      | D1 | D2 | D3 | D4 | D5 | D6 | D7 | D8 | D9 | D10 |
      | E1 | E2 | E3 | E4 | E5 | E6 | E7 | E8 | E9 | E10 |
      | F1 | F2 | F3 | F4 | F5 | F6 | F7 | F8 | F9 | F10 |
      | G1 | G2 | G3 | G4 | G5 | G6 | G7 | G8 | G9 | G10 |
      | H1 | H2 | H3 | H4 | H5 | H6 | H7 | H8 | H9 | H10 |
      | J1 | J2 | J3 | J4 | J5 | J6 | J7 | J8 | J9 | J10 |
      | I1 | I2 | I3 | J4 | I5 | I6 | I7 | I8 | I9 | I10 |


  Scenario: Can show game status
    When I enter coordinate:"show"
    And I press Submit button
    Then ships coordinates count should be:13
    And miss count should be:0

  Scenario: Cant hit invalid coordinate
    When I enter coordinate:"8734242"
    And I press Submit button
    Then table should contain text "*** Error ***"

  Scenario: Zero is handled as invalid input
    When I enter coordinate:"0"
    And I press Submit button
    Then table should contain text "*** Error ***"


  Scenario: Can RESET/SHOW game in sequence
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships coordinates count should be:0
    And miss count should be:0
    When I enter coordinate:"show"
    And I press Submit button
    Then ships coordinates count should be:13
    And miss count should be:0
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships coordinates count should be:0
    And miss count should be:0
    When I enter coordinate:"show"
    And I press Submit button
    Then ships coordinates count should be:13
    And miss count should be:0
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships coordinates count should be:0
    And miss count should be:0
    When I enter coordinate:"show"
    And I press Submit button
    Then ships coordinates count should be:13
    And miss count should be:0
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships coordinates count should be:0
    And miss count should be:0
    When I enter coordinate:"show"
    And I press Submit button
    Then ships coordinates count should be:13
    And miss count should be:0


  Scenario: Can reset game
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships coordinates count should be:0
    And miss count should be:0


  Scenario Outline: Can RESET/SHOW game in different sessions
    When I enter coordinate:"<command>"
    And I press Submit button
    Then ships coordinates count should be:<ships>
    And miss count should be:<miss>
    Examples:
      | command | ships | miss |
      | reset   | 0     | 0    |
      | show    | 13    | 0    |
#      | show    | 13    | 0    | //TODO: Uncomment when bug I3-456 is fixed
      | show    | 13    | 0    |
      | show    | 13    | 0    |
      | reset   | 0     | 0    |
      | reset   | 0     | 0    |
      | reset   | 0     | 0    |
      | show    | 13    | 0    |
      | show    | 13    | 0    |


  Scenario: Can hit coordinate
    When I enter coordinate:"A5"
    And I press Submit button
    Then message should be correct
    And enter form should contain text "coordinates"


  Scenario: Miss count is correct
    When I enter coordinate:"A1"
    And I press Submit button
    Then miss count should be:0


  Scenario: Cant hit blank coordinate
    When I enter coordinate:""
    And I press Submit button
    Then table should contain text "Error"
