# Java Regular Expressions (Regex) Cheat Sheet

## Table of Contents

1. [Basic Regex Characters](#1-basic-regex-characters)
2. [Quantifiers](#2-quantifiers)
3. [Anchors](#3-anchors)
4. [OR / Grouping](#4-or--grouping)
5. [Java-Specific Escape Rule](#5-java-specific-escape-rule-)
6. [Java Regex API](#6-java-regex-api)
7. [Pattern + Matcher](#7-pattern--matcher)
8. [`find()` vs `matches()`](#8-find-vs-matches-)
9. [Common Interview Regex Patterns](#9-common-interview-regex-patterns-)
10. [Greedy vs Lazy](#10-greedy-vs-lazy)
11. [Lookahead — Advanced but Useful](#11-lookahead--advanced-but-useful)
12. [Quick Java OA Cheat Sheet](#12-quick-java-oa-cheat-sheet-)
13. [Most Useful Java String Operations](#most-useful-java-string-operations)
14. [Top 5 to Memorize for Coding OAs](#-top-5-to-memorize-for-coding-oas)

---

## 1. Basic Regex Characters

| Regex | Meaning | Example |
|---|---|---|
| `.` | Any character | `a.c` → `abc`, `a1c` |
| `\d` | Digit `[0-9]` | `123` |
| `\D` | Non-digit | `a` |
| `\w` | Word character `[a-zA-Z0-9_]` | `hello_1` |
| `\W` | Non-word character | `@`, `#` |
| `\s` | Whitespace | space, tab |
| `\S` | Non-whitespace | `abc` |
| `[abc]` | a, b, or c | `a` |
| `[^abc]` | Anything except a, b, c | `x` |
| `[a-z]` | Lowercase letter | `a` |
| `[A-Z]` | Uppercase letter | `A` |
| `[0-9]` | Digit | `5` |

---

## 2. Quantifiers

| Regex | Meaning | Example |
|---|---|---|
| `*` | 0 or more | `a*` → `""`, `a`, `aaa` |
| `+` | 1 or more | `a+` → `a`, `aaa` |
| `?` | 0 or 1 | `a?` |
| `{n}` | Exactly n | `\d{3}` → `123` |
| `{n,}` | At least n | `\d{3,}` |
| `{n,m}` | Between n and m | `\d{2,4}` |

### Common examples

```text
\d+       → one or more digits
\d{3}     → exactly 3 digits
[a-z]+    → one or more lowercase letters
\w+       → one or more word characters
```

---

## 3. Anchors

| Regex | Meaning |
|---|---|
| `^` | Beginning of string |
| `$` | End of string |
| `\b` | Word boundary |
| `\B` | Not a word boundary |

Examples:

```text
^\d+$        → entire string must contain only digits
^[A-Z].*     → starts with uppercase letter
.*@.*\.com$  → ends with .com
\bcat\b      → matches "cat", but not "category"
```

---

## 4. OR / Grouping

```text
cat|dog
```

Matches either `cat` or `dog`.

```text
(cat|dog)
```

Groups `cat` or `dog`.

```text
^(cat|dog)$
```

Entire string must be exactly `cat` or `dog`.

---

## 5. Java-Specific Escape Rule ⚠️

Regex uses:

```text
\d
```

But a Java String requires:

```java
"\\d"
```

Example:

```java
String regex = "\\d+";
```

Not:

```java
String regex = "\d+";  // ❌
```

### Common conversions

| Regex | Java String |
|---|---|
| `\d` | `"\\d"` |
| `\w` | `"\\w"` |
| `\s` | `"\\s"` |
| `\b` | `"\\b"` |
| `\.` | `"\\."` |
| `\D` | `"\\D"` |

---

## 6. Java Regex API

The two most important classes:

```java
import java.util.regex.Pattern;
import java.util.regex.Matcher;
```

### `String.matches()`

Useful for simple validation:

```java
String s = "12345";

boolean result = s.matches("\\d+");
```

`matches()` checks the **entire string**.

```java
"12345".matches("\\d+")     // true
"abc123".matches("\\d+")    // false
```

---

## 7. Pattern + Matcher

Useful when searching inside a string.

```java
Pattern pattern = Pattern.compile("\\d+");
Matcher matcher = pattern.matcher("Order 123 has 45 items");

while (matcher.find()) {
    System.out.println(matcher.group());
}
```

Output:

```text
123
45
```

Remember:

```text
matches() → validates the entire string
find()    → searches for matching portions
```

---

## 8. `find()` vs `matches()` ⭐

```java
String s = "abc123xyz";

s.matches("\\d+");    // false
```

The entire string isn't digits.

But:

```java
Pattern p = Pattern.compile("\\d+");
Matcher m = p.matcher(s);

m.find();             // true
m.group();            // "123"
```

This distinction is very common in Java coding interviews.

---

# 9. Common Interview Regex Patterns ⭐⭐⭐

## Only digits

```java
"\\d+"
```

Example:

```java
"12345".matches("\\d+")   // true
"123a5".matches("\\d+")   // false
```

---

## Only letters

```java
"[a-zA-Z]+"
```

---

## Letters + digits

```java
"[a-zA-Z0-9]+"
```

---

## Alphanumeric

```java
"^[a-zA-Z0-9]+$"
```

---

## Exactly 5 digits

```java
"^\\d{5}$"
```

---

## US ZIP code

```java
"^\\d{5}(-\\d{4})?$"
```

Matches:

```text
12345
12345-6789
```

---

## Phone number

```java
"^\\d{3}-\\d{3}-\\d{4}$"
```

Matches:

```text
123-456-7890
```

---

## Email — simple interview version

```java
"^[\\w.-]+@[\\w.-]+\\.\\w+$"
```

---

## Starts with `A`

```java
"^A.*"
```

---

## Ends with `.com`

```java
".*\\.com$"
```

---

## Contains a digit

```java
".*\\d.*"
```

---

## Does NOT contain a digit

```java
"\\D*"
```

---

## Remove all spaces from a string

Use `replaceAll()`:

```java
String s = "Hello World Java";

String result = s.replaceAll("\\s+", "");

System.out.println(result);
```

Output:

```text
HelloWorldJava
```

### Important

- `" "` → matches only a normal space
- `"\\s"` → matches whitespace such as spaces, tabs, and newlines
- `"\\s+"` → matches one or more consecutive whitespace characters

For example:

```java
String s = "Hello   World\tJava";

String result = s.replaceAll("\\s+", "");

System.out.println(result);
```

Output:

```text
HelloWorldJava
```

---

## Replace multiple spaces with a single space ⭐

This is a very common interview operation.

```java
String s = "Hello    World   Java";

String result = s.replaceAll("\\s+", " ");

System.out.println(result);
```

Output:

```text
Hello World Java
```

If leading/trailing spaces may exist:

```java
String result = s.replaceAll("\\s+", " ").trim();
```

Example:

```text
"  Hello    World   Java  "
            ↓
"Hello World Java"
```

---

## Split a sentence into words ⭐⭐⭐

Use `split()`:

```java
String sentence = "Java is a powerful language";

String[] words = sentence.split("\\s+");

for (String word : words) {
    System.out.println(word);
}
```

Output:

```text
Java
is
a
powerful
language
```

### Why `\\s+`?

```text
\\s   → whitespace
+     → one or more
```

Therefore:

```java
sentence.split("\\s+")
```

handles multiple spaces and tabs.

Example:

```java
String sentence = "Java   is\tvery   powerful";

String[] words = sentence.split("\\s+");
```

Produces:

```text
Java
is
very
powerful
```

### ⚠️ Leading whitespace

If the sentence may start with whitespace:

```java
String sentence = "   Java   is a language";

String[] words = sentence.trim().split("\\s+");
```

---

## Split by a specific delimiter

### Comma

```java
String s = "Java,Python,Go";

String[] languages = s.split(",");
```

### Comma with optional spaces

```java
String s = "Java, Python, Go";

String[] languages = s.split(",\\s*");
```

Result:

```text
Java
Python
Go
```

### Pipe `|`

`|` is a regex special character, so escape it:

```java
String s = "Java|Python|Go";

String[] languages = s.split("\\|");
```

---

## Extract all numbers from a string

```java
String s = "Order 123 has 45 items";

Pattern p = Pattern.compile("\\d+");
Matcher m = p.matcher(s);

while (m.find()) {
    System.out.println(m.group());
}
```

Output:

```text
123
45
```

---

## Extract all words

```java
String s = "Hello Java World";

Pattern p = Pattern.compile("\\w+");
Matcher m = p.matcher(s);

while (m.find()) {
    System.out.println(m.group());
}
```

Output:

```text
Hello
Java
World
```

---

# 10. Greedy vs Lazy

### Greedy

```text
.*
```

Matches as much as possible.

### Lazy

```text
.*?
```

Matches as little as possible.

Example:

```java
String s = "<b>Hello</b><b>World</b>";
```

Greedy:

```text
<b>.*</b>
```

Can consume:

```text
<b>Hello</b><b>World</b>
```

Lazy:

```text
<b>.*?</b>
```

Finds:

```text
<b>Hello</b>
<b>World</b>
```

---

# 11. Lookahead — Advanced but Useful

## Positive lookahead

```text
(?=.*\d)
```

Means the string must contain a digit somewhere.

Example: password containing at least one digit:

```java
"^(?=.*\\d).+$"
```

---

## Negative lookahead

```text
(?!...)
```

Example: must not start with `abc`:

```java
"^(?!abc).*"
```

---

# 12. Quick Java OA Cheat Sheet 🚀

```text
\d       digit
\D       non-digit
\w       word character
\W       non-word character
\s       whitespace
\S       non-whitespace
.        any character

*        0 or more
+        1 or more
?        0 or 1
{n}      exactly n
{n,}     at least n
{n,m}    n to m

^        beginning
$        end
\b       word boundary

[abc]    a/b/c
[^abc]   NOT a/b/c
[a-z]    lowercase
[A-Z]    uppercase
[0-9]    digit

(a|b)    a OR b
(...)    capturing group

.*       anything (greedy)
.*?      anything (lazy)

Java:
\d  → \\d
\.  → \\.
\s  → \\s
\b  → \\b
```

## Most Useful Java String Operations

```java
// Validate entire string
str.matches("\\d+");

// Replace/remove whitespace
str.replaceAll("\\s+", "");

// Normalize multiple spaces
str.replaceAll("\\s+", " ").trim();

// Split sentence into words
str.trim().split("\\s+");

// Split comma-separated values
str.split(",\\s*");

// Find matches inside a string
Pattern.compile("\\d+").matcher(str).find();
```

## ⭐ Top 5 to Memorize for Coding OAs

```java
// 1. Only digits
"\\d+"

// 2. Remove all whitespace
str.replaceAll("\\s+", "")

// 3. Replace multiple whitespace with one space
str.replaceAll("\\s+", " ").trim()

// 4. Split sentence into words
str.trim().split("\\s+")

// 5. Find numbers inside a string
Pattern.compile("\\d+").matcher(str)
```
