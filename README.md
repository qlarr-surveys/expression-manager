# Survey State Management
This library manages and abstracts the different aspects of the survey state during survey design, rendering, submission and navigation. 

The core engine, Expression Manager, is a powerful portable engine that enables developers to build both the survey/form UI and back-end without worrying about the state or data, regardless of the programming language or environment, greatly speeding up development.

## What is the survey state
A survey state in Qlarr is a comprehensive representation of the data that drives the survey logic and UI. It includes:
### 1. Initial State - From Survey Definition
 - Survey Structure: The organization, order, and sampling of components such as pages, questions, and answers.
 - Initial State: Default values, including language settings and other predefined data.
### 2. User Interaction with the Survey UI - Saved to DB
 - User Input: All answers provided by the user throughout the survey.
 - Language Selection: The chosen language for the survey session.
 - Navigation: User actions such as moving forward, backward, or jumping between sections.
### 3. Dynamic UI State - Computed during runtime
This is the state that defines how the UI should behave
 - Progress: Which part of the survey the user is on
 - Relevance: Which questions are relevant/showing and which are hidden
 - Validity: Which questions has valid input and which are not
 - Conditional Formatting: Dynamically applied styles and formats based on survey logic

