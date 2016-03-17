// Core
var streamCombiner = require('stream-combiner2');
var runSequence = require('run-sequence');
var fs = require('fs');
var gulp = require('gulp');
var gutil = require('gulp-util');
var sourcemaps = require('gulp-sourcemaps');
var path = require('path');
var postcss = require('gulp-postcss');

// Plugins
var devtools = require('postcss-devtools');
var autoprefixer = require('autoprefixer');
var doiuse = require('doiuse');
var stylelint = require('stylelint');
var postcssImport = require('postcss-import');
var simpleVars = require('postcss-simple-vars');
var functions = require('postcss-functions');
var mixins = require('postcss-mixins');
var reporter = require('postcss-reporter');
var mqpacker = require('css-mqpacker');
var cssnano = require('cssnano');
var nested = require('postcss-nested');
var postcssCalc = require('postcss-calc');
var media = require('postcss-custom-media');
var selectors = require('postcss-custom-selectors');
var conditionals = require('postcss-conditionals');

var options = {
  browsers: ['last 2 versions']
};

gulp.task('css:lint', function() {
  return gulp.src('./src/**/*.css')
  .pipe(postcss([
    doiuse({
      browsers: options.browsers,
        ignore: ['css-appearance', 'css-resize', 'kerning-pairs-ligatures', 'font-feature', 'flexbox', 'rem', 'text-size-adjust', 'pointer-events', 'word-break']
    }),
    stylelint,
    reporter({clearMessages: true}),
  ]));
});

var processors = [
    postcssImport({glob: true}),
    mixins,
    simpleVars,
    media,
    conditionals,
    selectors,
    require('postcss-custom-properties'),
    nested,
    require('postcss-selector-not'),
    postcssCalc,
    require('postcss-contrast'),
    require('postcss-logical-props'),
    require('postcss-flexbugs-fixes'),
    autoprefixer({ browsers: options.browsers }),
    mqpacker,
    cssnano
];

function processCSS() {
  return streamCombiner.obj(
    sourcemaps.init(),
    postcss(processors).on("error", gutil.log),
    sourcemaps.write('.')
  );
}

gulp.task('css', ['css:lint'], function () {
  return gulp.src('./src/main.css').pipe(processCSS())
    .pipe(gulp.dest('./styles'));
});

// General
gulp.task('watch', function () { gulp.watch(['./src/**/*.css'], ['css']); });
gulp.task('default', ['css']);

