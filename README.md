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

### Options
```
-r  The registry extension endpoint to contact to obtain information about public HTRC worksets.

-o  The full path of the output file. The default value is "HTRCPublicWorksets.json".
```

## Output

The tool creates an output file, HTRCPublicWorksets.json by default, which
contains a JSON object per line. Each JSON object lists an HTRC volume identifier and
the set of HTRC public worksets which contain the associated volume. An
example is included below.

```
{"filename": "uiug.30112072479006", "HTRC_public_worksets": ["Niue@mfall3"]}
{"filename": "uc1.b5099571", "HTRC_public_worksets": ["spanish_publicdomain@miaochen"]}
```
