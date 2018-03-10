@test
Feature: Battleships tests http://www.techhuddle.com/tests/battleships/v4test/index.php

  Scenario: Hit coordinate
    Given user is on BattleShips Page
    When I enter coordinate:"A5"
    And I press Submit button
    Then message should be correct

  Scenario: Show battleships current status
    Given user is on BattleShips Page
    When I enter coordinate:"show"
    And I press Submit button
    Then ships count should be:13
    And miss count should be:0
    And remain count should be:87

    @dev
  Scenario: Reset battleships game
    Given user is on BattleShips Page
    When I enter coordinate:"reset"
    And I press Submit button
    Then ships count should be:0
    And miss count should be:0
    And remain count should be:100


