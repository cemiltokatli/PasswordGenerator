# Password Generator
Password Generator is a small Java library that allows you to generate random passwords with specific criteria. You can generate random passwords that consist of letters, numbers or symbols in the given length.

## Installation
Password Generator is available as a downloadable `.jar` java library. You can download the JAR file and directly import to your project. The current release version is 1.0.

* [Download **password-generator-1.0.jar** core library](http://cemiltokatli.com/libs/password-generator/password-generator-1.0.jar)
* [Download **password-generator-1.0-sources.jar** optional sources jar](http://cemiltokatli.com/libs/password-generator/password-generator-1.0-sources.jar)
* [Download **password-generator-1.0-javadoc.jar** optional javadoc jar](http://cemiltokatli.com/libs/password-generator/password-generator-1.0-javadoc.jar)

### Maven
If you use Maven to manage the dependencies in your project, you do not need to download the jar file; just place the following code block into your POM's `<dependencies>` section:

```
<dependency>
  <!-- Password Generator @ https://github.com/cemiltokatli/PasswordGenerator -->
  <groupId>io.github.cemiltokatli.passwordgenerator</groupId>
  <artifactId>password-generator</artifactId>
  <version>1.0</version>
</dependency>
```

### Building from source
If you want to build from source, it's best to use git so that you can stay up to date, and be able to contribute your changes back:

```
git clone https://github.com/cemiltokatli/PasswordGenerator.git
cd PasswordGenerator
mvn install
```

#### Tests
If you are building from source and make some changes, you might want to test your changes. Password Generator uses [JUnit](https://junit.org/junit5/) for testing and all test cases are located under the `src/test/` folder. The data prepared for testing purposes are located as [JSON](http://json.org/) files under the `src/test/resources` directory.

To run tests, you can use the `mvn test`command.

### Dependencies
Password Generator is entirely self contained and has no dependencies but for testing, it uses [JUnit](https://junit.org/junit5/) and [stleary's JSON library](https://github.com/stleary/JSON-java).

Password Generator runs on Java 9 or up.

## Usage
To generate a random password, you should first create a `Password` object by using the static `createPassword` method of the `Password` class. [Click here to see the reference](http://cemiltokatli.com/libs/password-generator/password-generator-1.0-javadoc/com/cemiltokatli/passwordgenerate/package-summary.html)

```
Password pass = Password.createPassword();
System.out.println(pass.generate());
```

You can call the `createPassword` method in four different ways.

The first one creates a new `Password` object with initial values.

```
public static Password createPassword();
````

In this case, the minimum length of password will be 5, maximum length will be 10 and the password will consist of alphanumeric characters and symbols.

You can also use the second version of the `createPassword` method to create a new `Password` object with the given type.

```
public static Password createPassword(PasswordType type);
```

The second version takes an argument of type `PasswordType` enum. The `type` means the type of the characters that the password will consist of.

For example, the following code block creates a new password with only letters.

```
Password pass = Password.createPassword(PasswordType.ALPHA);
System.out.println(pass.generate());
```

`PasswordType` enum has the following constants:

- `ALL` : Letters, numbers and symbols
- `ALPHA` : Only letters
- `NUMERIC` : Only numbers
- `ALPHANUMERIC` : Only letters and numbers
- `SYMBOLS` : Only symbols

The code block below creates a new password with alphanumeric characters.

```
Password pass = Password.createPassword(PasswordType.ALPHANUMERIC);
System.out.println(pass.generate());
```

There are also third and fourth versions that allow you to set a minimum and maximum length for the password to be generated.

```
public static Password createPassword(PasswordType type, int minLength);
public static Password createPassword(PasswordType type, int minLength, int maxLength);
```

In the following example, the password won't be shorter than 10 characters.

```
Password pass = Password.createPassword(PasswordType.ALPHANUMERIC, 10);
System.out.println(pass.generate());
```

In the following code block, the password won't be shorter than 15 characters and longer than 20 characters.

```
Password pass = Password.createPassword(PasswordType.ALPHANUMERIC, 15, 20);
System.out.println(pass.generate());
```

You can also set those properties later by using setter methods.

```
Password pass = Password.createPassword();
pass.setType(PasswordType.SYMBOLS);
pass.setMinLength(20);
pass.setMaxLength(50);
System.out.println(pass.generate());
```

If you want to exclude some characters and don't want the password to contain them, you can use the `addExcludedChar` and `addExcludedChars` methods to prevent the password from containing them.

In the following example, the `generate` method will create a password that consist of only symbols but the password won't  contain the given characters `$, @, #, %, =, +`.

```
Password pass = Password.createPassword();
pass.setType(PasswordType.SYMBOLS);
pass.addExcludedChar('$');
pass.addExcludedChar('@');
pass.addExcludedChar('#');
pass.addExcludedChars(new char[]{'%','=','+'});
System.out.println(pass.generate());
```