# Usage
## Analyze single entry
- Go to **Code** -> **Analyze Stack Trace or Thread Dump**
- Paste to input paths of the failed inspection and press **OK** button. For example:
  ```	
  + /wp-content/plugins/akismet/class.akismet-rest-api.php:239 WEAK WARNING 'false'
  - /wp-includes/functions.php:6461 WEAK WARNING $wp_max_limit
  + /wp-content/plugins/akismet/class.akismet-rest-api.php:240 WEAK WARNING '0'
  ```
  ## Analyze remote Teamcity projects

  ### Settings
  - Go to **Main** -> **Prefernces** -> **Test Analyzer Settings**
  - Fill list of project ids(separated by new line) to analyze
  ## Analyze
  - Go to **Code** -> **Analyze Build Runs**
  <img width="1680" alt="Screenshot 2023-01-19 at 02 03 08" src="https://user-images.githubusercontent.com/5507490/213305277-6a72183b-a195-4658-8dce-65747fd1fd54.png">
  
  
