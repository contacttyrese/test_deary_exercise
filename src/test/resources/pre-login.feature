@test @android @emulator
Feature: Pre-Login
  As a user
  I want to navigate to login page
  In order to login

  Scenario: user can see Friends and navigate to Save in tour
    Given user is on friends on landing tour
    When user swipes right
    Then user is on save on landing tour

  Scenario: user can see Save and navigate to Find in tour
    Given user is on save on landing tour
    When user swipes right
    Then user is on find on landing tour

  Scenario: user can see Find and navigate to Import in tour
    Given user is on find on landing tour
    When user swipes right
    Then user is on import on landing tour

  Scenario: user can see Import and navigate to Friends in tour
    Given user is on import on landing tour
    When user swipes right
    Then user is on friends on landing tour

    @initialtest
  Scenario: user is able to navigate to login screen
    Given user is on landing tour
    When user selects skip
    And user denies permission on login screen
    Then user is on login screen