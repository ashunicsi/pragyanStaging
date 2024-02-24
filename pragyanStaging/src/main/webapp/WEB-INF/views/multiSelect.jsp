<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Multi-Select Dropdown</title>
</head>
<body>
    <form action="multiSelectForm" method="post">
        <h2>Select Multiple Items:</h2>
        <select name="selectedItems" multiple>
            <option value="item1">Item 1</option>
            <option value="item2">Item 2</option>
            <option value="item3">Item 3</option>
            <!-- Add more options as needed -->
        </select>
        <br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>