# School Management System (Console-Based Java)

This project is a console-based School Management System designed with Object-Oriented Programming principles.

## Features

- Student registration, update, and class allocation
- Teacher registration and update
- Classroom setup and teacher association
- Attendance recording
- Results recording and grade generation
- Fee payment tracking and balance calculation
- Reports:
  - student list
  - fee balances
  - attendance summary
  - academic summary

## OOP Concepts Demonstrated

- **Abstraction**: `Person` is abstract with a role-specific `displayDetails()`.
- **Inheritance**: `Student` and `Teacher` extend `Person`.
- **Encapsulation**: Private fields with validated setters/getters.
- **Polymorphism**:
  - Runtime: `displayDetails()` overridden in `Student` and `Teacher`.
  - Compile-time style: student search via different methods (by ID, name, class).
- **Relationships**:
  - IS-A: `Student`/`Teacher` -> `Person`
  - HAS-A: `Student` has attendance, results, and fee payments
  - ASSOCIATION: `Teacher` linked to `Classroom`

## Project Structure

- `src/sms/Main.java`
- `src/sms/SchoolManagementSystem.java`
- `src/sms/Person.java`
- `src/sms/Student.java`
- `src/sms/Teacher.java`
- `src/sms/Classroom.java`
- `src/sms/AttendanceRecord.java`
- `src/sms/AttendanceStatus.java`
- `src/sms/ResultRecord.java`
- `src/sms/FeePayment.java`

## How To Run

From the project root:

```bash
javac -d out src/sms/*.java
java -cp out sms.Main
```

### Windows (Command Prompt)

```bat
javac -d out src\sms\*.java
java -cp out sms.Main
```

### Windows (PowerShell)

```powershell
javac -d out src/sms/*.java
java -cp out sms.Main
```

Using Maven:

```bash
mvn compile
mvn exec:java
```

### Windows Maven (Command Prompt or PowerShell)

```bat
mvn compile
mvn exec:java
```

## Run in IntelliJ IDEA

1. Open IntelliJ IDEA and choose **Open**.
2. Select the `studentmbizi` folder.
3. Make sure a JDK is configured:
   - Go to **File > Project Structure > Project**.
   - Set **Project SDK** to Java 17+.
4. In the Project pane, open `src/sms/Main.java`.
5. Click the green Run icon next to `main()` and run `Main`.
6. Use the Run console at the bottom to interact with the menu.

If IntelliJ says no SDK/JDK is configured, install a JDK first and select it in Project Structure.

For Maven projects in IntelliJ:

1. Open the project folder.
2. IntelliJ should detect `pom.xml` and show **Load Maven Project** (click it).
3. Ensure Project SDK is Java 17+.
4. Open `src/sms/Main.java` and run `main()`, or use Maven tool window -> Plugins -> `exec` -> `exec:java`.

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
