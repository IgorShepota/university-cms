# User Stories for University CMS

## Teacher's Schedule Viewing

**As a** Teacher,
**I want to** view my class timetable,
**So that** I can manage my teaching schedule efficiently.

### Acceptance Criteria:

- **Given** I am logged in as a Teacher,
  **When** I navigate to the `My Schedule` menu,
  **Then** I should be presented with my current teaching schedule.
- **Given** I am viewing my schedule,
  **When** I select a specific date or a date range,
  **Then** I should see the schedule for the selected date or range.
- **Given** I am looking at my schedule for a month,
  **When** I select a month from the calendar,
  **Then** I should see all the classes I will be teaching for the entire month.
- **Given** I am looking at my schedule for a day,
  **When** I pick a day from the calendar,
  **Then** I should see all the classes I am scheduled to teach on that particular day.

## Student's Schedule Viewing

**As a** Student,
**I want to** view my class timetable,
**So that** I can plan my study and personal activities accordingly.

### Acceptance Criteria:

- **Given** I am logged in as a Student,
  **When** I navigate to the `My Schedule` menu,
  **Then** I should be presented with my current class schedule.
- **Given** I am viewing my schedule,
  **When** I select a specific date,
  **Then** I should see the classes scheduled for that particular day.
- **Given** I am viewing my schedule,
  **When** I select a month,
  **Then** I should see the classes scheduled for the entire selected month.
- **Given** I am viewing the schedule for a day,
  **When** I click on a class,
  **Then** I should see the details of the class including subject, time, teacher, and classroom.
