START APP

// Create main window
CreateWindow("My To-Do List")

// Create UI elements
CreateTextBox(taskInput)
CreateButton("Add", onAddButtonClicked)
CreateListWidget(taskList)

// When Add button is clicked
function onAddButtonClicked:
    taskText = taskInput.getText()
    if taskText is not empty:
        createNewTaskItem(taskText)
        taskInput.clear()

// Create new task in the list
function createNewTaskItem(taskText):
    listItem = taskList.addItem()
    listItem.setText(taskText)
    listItem.addCheckBox(onCheckBoxChanged)
    listItem.addDeleteButton(onDeleteButtonClicked)

// When checkbox is clicked
function onCheckBoxChanged(listItem):
    if listItem.isChecked():
        listItem.setTextStrikethrough(true)  // visually mark as done
    else:
        listItem.setTextStrikethrough(false)

// When delete button is clicked
function onDeleteButtonClicked(listItem):
    taskList.removeItem(listItem)

// OPTIONAL: Save and load tasks to file
function saveTasksToFile:
    for each task in taskList:
        save (task.text, task.doneState) to file

function loadTasksFromFile:
    read file line by line
    for each line:
        createNewTaskItem(taskText)
        set task done state
