# HTRC-WorksetMetadataForBookworm

A tool to obtain information about public HTRC worksets and create a file
which contains workset metadata that can be added to Bookworm. 

## Build

Create an executable jar containing all dependencies, i.e., a fat jar, using
the following command:
```
mvn clean package
```

## Run
```
java  -jar HTRCWorksetMetadata.jar [-r regExtURL] [-o outputFilePath]
```
regExtUrl and outputFilePath are both optional arguments.

regExtUrl: the registry extension endpoint to contact to obtain information
about public worksets.

outputFilePath: the full path of the output file; the default value is
"HTRCPublicWorksets.json".

## Output

The tool creates an output file, HTRCPublicWorksets.json by default, which
contains a JSON object per line. Each JSON object lists an HTRC volume id and
the set of HTRC public worksets which contain the associated volume. An
example is included below.

```
{"filename": "uiug.30112072479006", "HTRC_public_worksets": ["Niue@mfall3"]}
{"filename": "uc1.b5099571", "HTRC_public_worksets": ["spanish_publicdomain@miaochen"]}
```
