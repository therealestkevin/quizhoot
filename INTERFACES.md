# Interfaces
## quizlet
### Input
1. Path to a JSON file.

   Format:
   ```json
   {
       url: "someurl.com",
       outPath: "path/to/out.json",
   }
   ```

### Output
Writes to `outPath`.

Format:
```json
{
    "array": [
        {
            "word": "hola",
            "def": "hello",
            "imageUrl": "image.com" or null,
        },
        .
        .
        .
    ],
}
```

## babu
### Input
1. Path to a JSON file

Format:
```json
  {
	"termsPath": "path/to/output/of/quizlet.json",
	"outPath": "path/to/out.json",
	"options": {
	    "reverse": boolean,
	    "natLang": boolean
	}
}
```

### Output
Writes to `outPath`

Format:
```json
{
    array: [
        {
            word: 'hello',
            choices: [
                { def: 'choice1', correct: boolean },
                { def: 'choice2', correct: boolean },
                { def: 'choice3', correct: boolean },
                { def: 'choice4', correct: boolean },
            ],
        },
        .
        .
        .
    ]
}
```
