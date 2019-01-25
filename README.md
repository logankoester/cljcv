# cljcv

> Produce a nicely formatted curriculum vitae or résumé in various formats.
<p align="center">
  <img src="doc/example.gif" alt="example" />
</p>

## How it works

Write your résumé in [edn](https://github.com/edn-format/edn), and **cljcv**
will produce a nicely formatted document for you.

You can then create variants that extend from your defaults to add cover
letters or make other adjustments for each company you apply to.

## Installation

Download the [latest release](https://github.com/logankoester/cljcv/releases),
then add an executable wrapper script to your $PATH, like this:

```bash
#!/bin/sh
exec /usr/bin/java -jar '/path/to/cljcv-$VERSION-SNAPSHOT-standalone.jar' "$@"
```

## Usage

<p align="center">
  <img src="doc/demo.gif" alt="demo" />
</p>

First, create a new project skeleton.

```
$ mkdir ~/cv
$ cd ~/cv
$ cljcv init
```

The file `data/default.clj` is your default CV.

If you run `cljcv watch` while you're editing, your documents will be rebuilt
each time you save. Some PDF readers will reload automatically when a file
changes, allowing you to preview your work. Specifically I recommend
[Evince](https://wiki.gnome.org/Apps/Evince).


The file `data/variant.clj` is an example of how to extend `default.clj` by adding a
cover letter. You may safely delete or rename it if, or create more.

You should also add your own avatar, signature, company logos, and skill icon images
to the `resources` directory, overwriting the placeholders that you find there.

When you're finished, run `cljcv build`.

## License

Copyright 2019 Logan Koester <logan@logankoester.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
