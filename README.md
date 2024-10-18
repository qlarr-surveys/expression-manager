# Survey State Management
This library manages and abstracts the different aspects of the survey state during survey design, rendering, submission and navigation. The core engine, **Expression Manager**, is a powerful portable engine that enables developers to build both the survey/form UI and back-end without worrying about the state or data, regardless of the programming language or environment, greatly speeding up development.

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

<img width="862" alt="Screenshot 2024-10-18 at 22 34 03" src="https://github.com/user-attachments/assets/5096a372-8e70-4898-a491-ec2dac716f41">


## The Domain-Specific Language (DSL) for Survey Design

Qlarr introduces a DSL that establishes a standardized approach to survey design. This DSL is integrated directly into the Expression Manager, ensuring that logic is both extendable and maintainable.

### Survey Components
Survey Components are the active building blocks of a survey... Each survey is built upon the core components
 - **Surveys**: The root component, a representation of the whole survey... a survey contains 1 or more pages
 - **Pages**: represents a page/screen view, contains one or more questions. has a unique code 
 - **Questions**: represents a single question, contains optional answers. has unique code
 - **Answers**: represents a single answer (text field) or choice (within an MCQ) can optionall contain nested answers
<img width="525" alt="Screenshot 2024-10-18 at 22 53 40" src="https://github.com/user-attachments/assets/d7e9e3cf-8149-48da-abdf-5a92ae2b96b3">


### Survey Components common attributes
- **Content**: Translatable, human-readable text for each component.
- **Code**: A unique identifier for each component to maintain consistent reference throughout the survey.
- **Children**: Nested elements, enabling hierarchical organization.
- **Instructions** are JavaScript-runnable commands that define the survey logic, such as
  - **Randomization**: Control over how questions or pages are presented to ensure unbiased responses.
  - **Sampling**: Selection of subsets of questions or answers based on predefined rules.
  - **Conditional Visibility**: Logic to show or hide specific questions or pages based on user input or other conditions.
  - **Skipping Logic**: Rules for jumping between groups or questions based on user actions or responses.
  - **Validation Conditions**: Rules to ensure that responses meet the necessary criteria before progressing.
 

## Survey Navigation

Qlarr provides flexible navigation options, allowing developers to tailor the user experience to different needs. The survey can be navigated in several ways:
- **All-in-One**: The entire survey can be displayed on a single page, allowing users to complete all sections without transitioning between pages.
- **Page-by-Page Navigation**: The survey can be broken into pages, where users progress one page at a time, moving forward or backward between sections.
- **Question-by-Question Navigation**: Each question can be presented individually, giving users a focused view of one question at a time, with navigation options to move forward or revisit previous questions.

<img width="847" alt="Screenshot 2024-10-18 at 22 28 26" src="https://github.com/user-attachments/assets/b166cda2-d07f-4ecb-adc9-368d5bd7068b">

This flexible navigation model ensures that surveys can be adapted to various use cases, from quick overviews to detailed, step-by-step progression. Qlarr supports navigation commands such as starting, moving forward, moving backward, jumping between sections, and resuming a survey where the user left off.

Here’s the section formatted in markdown:


## API
Expression Manager exposes 2 main public methods

### During Survey Design (Back-end)
- **Survey Design Validation**: The Expression Manager ensures that the survey structure and instructions are correctly designed, checking for completeness, consistency, and logic errors.
- **State Serialization**: It serializes the entire survey state into a JavaScript state machine (also known as a Dependency Map), which is used to manage the survey state later when the survey is run.

### During Survey Navigation (Back-end)
- The Expression Manager processes user navigation requests and emits the next survey state, ensuring a smooth and dynamic flow throughout the survey.

### During Survey Run (Front-end)
- The state machine delivers the relevant portion of the state to the front-end, allowing it to manage survey state changes, respond to user actions (e.g., showing/hiding components), validate inputs, and more.
