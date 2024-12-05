# Bus Seat Identification System

## Project Overview
A Java application that uses binary space partitioning to decode and analyze bus seat tickets for a tour group, implementing advanced stream processing and seat allocation logic.

testData.bat currently has LLRUFBBBRL removed, program should return LLRUFBBBRL as result.

## Key Features
- Binary space partitioning for precise seat identification
- Seat decoding algorithm with multiple parsing stages
- Stream-based ticket processing
- Unique seat ID calculation
- Identification of missing seat

## Seat Decoding Process
The application decodes seat information through a multi-stage process:

### Bus Selection
- First 3 characters (L/R) determine bus number
- Progressively narrows down bus selection
- Supports 8 total buses

### Deck Selection
- 4th character (U/D) determines deck level
- Supports 2 deck levels (upper/lower)

### Row Identification
- Next 4 characters (F/B) specify row
- Narrows down from 16 possible rows
- Uses binary space partitioning technique

### Column Determination
- Final 2 characters (L/R) specify seat column
- Identifies 1 of 4 columns per row

## Seat ID Calculation
Unique seat ID computed by:
- Bus × 128
- Deck × 64
- Row × 4
- Column (added directly)

## Technical Implementation
- Single Stream pipeline
- Lambda expressions
- Java Collectors
- Advanced stream processing techniques

## Input
- Batch file with encoded seat tickets
- Format: Alphanumeric sequence representing seat details

## Output
- Detailed seat information
- Highest seat ID
- Formatted console output

## Prerequisites
- Java Development Kit (JDK)
- Understanding of Stream API
- Familiarity with functional programming concepts

## Skills Demonstrated
- Advanced Java Stream processing
- Complex string parsing
- Algorithmic seat allocation
- Functional programming techniques