# Contributing to KoreX

Thanks for helping improve KoreX.

This repository contains an Android utility library plus a separate `SampleApp` demo. Keep changes small, focused, and easy to review.

## Before you start

- Read the [README](README.md) first.
- Prefer a single purpose per change.
- Keep public-facing code and documentation in English.
- Do not add AI attribution or `Co-Authored-By` lines to commits.

## Recommended workflow

1. Make the smallest useful change.
2. Update tests or the sample app when behavior changes.
3. Update the README if the public API changes.
4. Run the build before opening a pull request.

## Code style

- Use clear Kotlin naming.
- Keep public APIs small and readable.
- Prefer explicit intent over clever abstractions.
- Separate responsibilities into different files when it improves clarity.

## Testing

Run the relevant checks for the touched area. For most changes:

- `./gradlew build`

If you change runtime behavior in the sample app, also verify the demo still launches and the affected flow still works.

## Documentation

Update the README when you change:

- a public function
- a builder or DSL
- a permission flow
- a networking or notification contract
- a sample app flow that demonstrates the library

## Pull requests

PRs should explain:

- what changed
- why it changed
- how it was verified

If a change can be split into smaller reviewable pieces, split it.
