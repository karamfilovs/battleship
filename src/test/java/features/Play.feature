@test
Feature: Battleships tests http://www.techhuddle.com/tests/battleships/v4test/index.php

  Scenario: Hit coordinate
    Given user is on BattleShips Page
    When I enter coordinate:"A5"
    And I press Submit button
    Then message should be correct


  Scenario: Hit coordinate
    Given user is on BattleShips Page
    When I hit multiple coordinates:
     |A1|A2|A3|A4|A5|A6|A7|A8|A9|

  Scenario: Show battleships current status
    Given user is on BattleShips Page
    When I enter coordinate:"show"
    And I press Submit button
    Then ships count should be:13
    And miss count should be:0
    And remain count should be:87


  Scenario: Reset battleships game
    Given user is on BattleShips Page
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships count should be:0
    And miss count should be:0
    And remain count should be:100


