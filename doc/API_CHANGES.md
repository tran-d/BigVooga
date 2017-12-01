## External API Engine: 

Additional Classes:

### class OperationFactory

   Operation makeOperation(String operationName, Object... parameters)

   List<String> getParameters(String operationName)

   List<String> getOperations(String operationType)
   
### class ActionFactory

   List<String> getCategories()
   
   List<String> getActions(String category)

   Action makeAction(String actionName, Object... parameters)
   
   List<String> getParameters(String actionName)
   