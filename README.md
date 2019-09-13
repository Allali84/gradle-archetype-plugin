## gradle-archetype-plugin [![Build Status](https://travis-ci.org/Allali84/gradle-archetype-plugin.svg)](https://travis-ci.org/Allali84/gradle-archetype-plugin)

### Tasks
 * `cleanArchetype`: cleans the generated folders and files.
 * `generate`: generates projects from the template.

### Interactive Mode:
```
gradle cleanArch generate -i

### Token Format
 * In code: `@variable@`
 * In file name: `__variable__`
 
