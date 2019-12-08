Feature: Battleship game

  Background:
    Given user is on BattleShips Page

  @dev
  Scenario: Can show game current status
    When I enter coordinate:"show"
    And I press Submit button
    Then ships coordinates count should be:13

  Scenario: Can reset game
    When I enter coordinate:"reset"
    And I press Submit button
    Then miss count should be:0
    Then ships coordinates count should be:0
