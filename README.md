# group1.app — School Management System (Console-Based Java)

This project (**group1.app**) is a console-based School Management System designed with Object-Oriented Programming principles.

## Features

- Student registration, update, removal, and class allocation
- Teacher registration, update, and removal
- Classroom setup and teacher association
- Attendance recording
- Results recording and grade generation
- Fee payment tracking, balance calculation, and fee statements (payment history)
- Optional **Swing** GUI (`GuiMain`) with the same core operations
- **JUnit 5** unit tests under `src/test/java`
- Reports:
  - student list
  - fee balances
  - fee statements
  - attendance summary
  - academic summary

## OOP Concepts Demonstrated

- **Abstraction**: `Person` is abstract with a role-specific `displayDetails()`.
- **Inheritance**: `Student` and `Teacher` extend `Person`.
- **Encapsulation**: Private fields with validated setters/getters.
- **Polymorphism**:
  - Runtime: `displayDetails()` overridden in `Student` and `Teacher`.
  - Compile-time style: overloaded `searchStudent` usage (lookup by ID vs filtered lists by name or class).
- **Relationships**:
  - IS-A: `Student`/`Teacher` -> `Person`
  - HAS-A: `Student` has attendance, results, and fee payments
  - ASSOCIATION: `Teacher` linked to `Classroom`

## Project Structure

- `src/main/java/sms/Main.java` (console entry)
- `src/main/java/sms/GuiMain.java` (GUI entry)
- `src/main/java/sms/SchoolManagementGui.java`
- `src/main/java/sms/SchoolManagementSystem.java`
- `run-console.ps1` / `run-gui.ps1` (Windows: compile and run without fixing PATH)
- `src/test/java/sms/*Test.java` (JUnit)
- `src/main/java/sms/Person.java`
- `src/main/java/sms/Student.java`
- `src/main/java/sms/Teacher.java`
- `src/main/java/sms/Classroom.java`
- `src/main/java/sms/AttendanceRecord.java`
- `src/main/java/sms/AttendanceStatus.java`
- `src/main/java/sms/ResultRecord.java`
- `src/main/java/sms/FeePayment.java`

## How To Run

From the project root:

```bash
javac -d out src/main/java/sms/*.java
java -cp out sms.Main
```

Run GUI version from terminal:

```bash
javac -d out src/main/java/sms/*.java
java -cp out sms.GuiMain
```

### Windows (Command Prompt)

```bat
javac -d out src\main\java\sms\*.java
java -cp out sms.Main
```

GUI on Windows (Command Prompt):

```bat
javac -d out src\main\java\sms\*.java
java -cp out sms.GuiMain
```

### Windows (PowerShell)

```powershell
javac -d out src/main/java/sms/*.java
java -cp out sms.Main
```

GUI on Windows (PowerShell):

```powershell
javac -d out src/main/java/sms/*.java
java -cp out sms.GuiMain
```

If `java` or `javac` is not recognized (PATH), from the project root run:

```powershell
.\run-console.ps1
.\run-gui.ps1
```

Using Maven:

```bash
mvn compile
mvn exec:java
```

Run GUI with Maven:

```bash
mvn compile
mvn -Dexec.mainClass=sms.GuiMain exec:java
```

Run tests:

```bash
mvn test
```

### Windows Maven (Command Prompt or PowerShell)

```bat
mvn compile
mvn exec:java
```

## Run in IntelliJ IDEA

1. Open IntelliJ IDEA and choose **Open**.
2. Select the project folder (e.g. `group1.app`).
3. Make sure a JDK is configured:
   - Go to **File > Project Structure > Project**.
   - Set **Project SDK** to Java 17+.
4. In the Project pane, open `src/main/java/sms/Main.java`.
5. Click the green Run icon next to `main()` and run `Main`.
6. Use the Run console at the bottom to interact with the menu.

If IntelliJ says no SDK/JDK is configured, install a JDK first and select it in Project Structure.

For Maven projects in IntelliJ:

1. Open the project folder.
2. IntelliJ should detect `pom.xml` and show **Load Maven Project** (click it).
3. Ensure Project SDK is Java 17+.
4. Open `src/main/java/sms/Main.java` and run `main()`, or use Maven tool window -> Plugins -> `exec` -> `exec:java`.

To run GUI in IntelliJ:

1. Open `src/main/java/sms/GuiMain.java`.
2. Click the green Run icon next to `main()`.
3. Use the tabs:
   - Dashboard
   - Students
   - Teachers
   - Records
   - Reports

For Windows users in IntelliJ:

- Install JDK 17+ (Temurin/Oracle/OpenJDK).
- Set `JAVA_HOME` to the JDK folder if IntelliJ does not auto-detect it.
- In IntelliJ, set **Project SDK** to that JDK.

## GitHub Version Control

Yes, you can and should put this on your own GitHub repo.

### Initialize git and make first commit

```bash
git init
git add .
git commit -m "Initial console-based school management system"
```

### Connect to your GitHub repo and push

Create an empty repo on GitHub first, then run:

```bash
git remote add origin https://github.com/<your-username>/<your-repo-name>.git
git branch -M main
git push -u origin main
```

After this, future updates are:

```bash
git add .
git commit -m "Describe your changes"
git push
```
