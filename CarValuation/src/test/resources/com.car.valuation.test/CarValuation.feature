@carSearch
Feature: Car valuation details

  Scenario Outline: Reads the car input file and verify car valuation details and print car details which doesn't match
    Given Reads the car input text file <INPUT_FILE>
    When  Navigate to website and perform car valuation
    Then  Compare the output returned by car valuation website with output text file <OUTPUT_FILE>

    Examples:
      | INPUT_FILE    | OUTPUT_FILE    |
      | car_input.txt | car_output.txt |
