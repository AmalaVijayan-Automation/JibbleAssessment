package toDo

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys


import internal.GlobalVariable

public class ToDoFunctionalities {

	@Keyword
	def noToDoValidation(){
		WebUI.verifyElementNotVisible(findTestObject('ToDoMVC/main'), FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyElementNotVisible(findTestObject('ToDoMVC/footer'), FailureHandling.STOP_ON_FAILURE)
	}

	@Keyword
	def newToDoAutoFocus(){
		WebUI.verifyElementHasAttribute(findTestObject('ToDoMVC/textbox_new-todo'),'autofocus', 20)
	}


	@Keyword
	def newToDoCreate(){

		String toDoName = findTestData('InputValues/values').getValue(1, 1)
		WebUI.sendKeys(findTestObject('ToDoMVC/textbox_new-todo'), Keys.chord(toDoName,Keys.ENTER))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/textbox_todo_value', [('value') :toDoName]), 3)
	}

	@Keyword
	def newToDoCreateTrim(){

		String toDoNamewithSpace = findTestData('InputValues/values').getValue(1, 2)
		WebUI.sendKeys(findTestObject('ToDoMVC/textbox_new-todo'), Keys.chord(toDoNamewithSpace,Keys.ENTER))
		def trimmedvalue = toDoNamewithSpace.trim()
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/textbox_todo_value', [('value') :trimmedvalue]), 3)
	}

	@Keyword
	def markAllAsComplete(){

		WebUI.verifyElementNotVisible(findTestObject('ToDoMVC/toggle_all'), FailureHandling.STOP_ON_FAILURE)
		newToDoCreate()
		WebUI.verifyElementVisible(findTestObject('ToDoMVC/toggle_all'), FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/todo-list'), 3)
		WebUI.click(findTestObject('ToDoMVC/toggle_all'))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/todo-list1', [('value') :'completed']), 3)
		WebUI.verifyElementHasAttribute(findTestObject('ToDoMVC/todo_checkbox'),'checked', 20)
		WebUI.click(findTestObject('ToDoMVC/toggle_all'))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/todo-list1', [('value') :'']), 3)
		WebUI.click(findTestObject('ToDoMVC/toggle_all'))
		WebUI.click(findTestObject('ToDoMVC/btn_clearcompleted'))
		WebUI.verifyElementNotVisible(findTestObject('ToDoMVC/toggle_all'), FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyElementNotPresent(findTestObject('ToDoMVC/todo-list1', [('value') :'completed']), 3)
	}

	@Keyword
	def itemCreate(){

		newToDoCreate()
		String toDoName = findTestData('InputValues/values').getValue(1, 1)
		WebUI.click(findTestObject('ToDoMVC/toDoCheckbox'))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/todoparentobject', [('value') :toDoName, ('value1') :'completed']), 3)
	}

	@Keyword
	def itemEdit(){

		String toDoName = findTestData('InputValues/values').getValue(1, 1)
		WebUI.click(findTestObject('ToDoMVC/toDoCheckbox'))
		WebUI.doubleClick(findTestObject('ToDoMVC/todoparentobject', [('value') :toDoName, ('value1') :'']))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/todo-list1', [('value') :'editing']), 3)
		WebUI.click(findTestObject('ToDoMVC/textbox_new-todo'))
	}

	@Keyword
	def itemMouseover(){

		WebUI.mouseOver(findTestObject('ToDoMVC/todo-list'))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/destroybtn'), 3)
	}

	@Keyword
	def editItem(){

		newToDoCreate()
		String toDoName = findTestData('InputValues/values').getValue(1, 1)
		def newToDo = toDoName + System.currentTimeMillis()
		WebUI.doubleClick(findTestObject('ToDoMVC/list'))
		WebUI.sendKeys(findTestObject('ToDoMVC/edit_mode_text'), Keys.chord(newToDo,Keys.ENTER))
		WebUI.verifyElementPresent(findTestObject('ToDoMVC/textbox_todo_value', [('value') :newToDo]), 3)
	}
}
