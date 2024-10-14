#Notes

##Success for Binding 
###1
enable in build.gradle.kts (module:app)
```
buildFeatures{
    viewBinding = true
}
```
###2
create a variable to bind to
the value will always be related to the file working in
```
private lateinit var binding: ActivityMainBinding
```

###3
inflate the binding and set view to binding.root
```
binding = ActivityMainBinding.inflate(layoutInflater) 
setContentView(binding.root)
```

###4
bind widget from xml to code using binding.<widget_id>
```
binding.questionTextView.setText(questionTextResId)
```