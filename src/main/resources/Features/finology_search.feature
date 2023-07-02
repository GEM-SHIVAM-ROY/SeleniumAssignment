Feature: Searching for Companies on Finology Ticker
  Scenario Outline: Searching for Different Companies
    Given the user is on the Finology Ticker website
    When the user clicks on the search box
    And the user enters "<company>" in the search box
    Then the user should see the search results for "<company>"
    And the user fetches and store the details in the Excel sheet "<Row>"
    And convert excel data to json array

    Examples:
      | company   |Row|
      | Reliance  |0  |
      | Tata    |4    |



