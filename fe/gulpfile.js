var gulp = require('gulp');
var copy = require('gulp-copy');
var clean = require('gulp-clean');
var uglify = require('gulp-uglify');
var cleanCSS = require('gulp-clean-css');
var sourcemaps = require('gulp-sourcemaps');

var path = {
  app: {
    htmls: 'src/*.html',
    scripts: 'src/js/**/*',
    styles: 'src/css/**/*',
    images: 'src/img/*',
  },
  vendor: {
    scripts: [
      'src/lib/jquery.min.js',
      'src/lib/handlebars-v4.0.5.js'
    ]
  },
  dist: {
    base: 'dist',
    htmls: 'dist',
    scripts: 'dist/js',
    styles: 'dist/css',
    images: 'dist/img',
    vendor: 'dist/lib'
  }
};

// clean all prev resource
gulp.task('clean', function () {
  return gulp
    .src(path.dist.base, {read: false})
    .pipe(clean({force: true}));
});

// copy html resource
gulp.task('htmls', function () {
  return gulp
    .src(path.app.htmls)
    .pipe(copy(path.dist.htmls, {prefix: 1}))
    .pipe(gulp.dest(path.dist.htmls));
});

// copy scripts resource
gulp.task('scripts', function () {
  return gulp
    .src(path.app.scripts)
    .pipe(sourcemaps.init())
    .pipe(uglify())
    .pipe(sourcemaps.write())
    .pipe(copy(path.dist.scripts, {prefix: 2}))
    .pipe(gulp.dest(path.dist.scripts));
});

// copy styles resource
gulp.task('styles', function () {
  return gulp
   .src(path.app.styles)
    .pipe(sourcemaps.init())
    .pipe(cleanCSS())
    .pipe(sourcemaps.write())
    .pipe(copy(path.dist.styles, {prefix: 2}))
    .pipe(gulp.dest(path.dist.styles));
});

// copy vendor resource
gulp.task('vendor', function () {
  return gulp
    .src(path.vendor.scripts)
    .pipe(copy(path.dist.vendor, {prefix: 2}))
    .pipe(gulp.dest(path.dist.vendor));
});

// copy static image resource
gulp.task('images', function () {
  return gulp
    .src(path.app.images)
    .pipe(copy(path.dist.images, {prefix: 2}))
    .pipe(gulp.dest(path.dist.images));
});

// build. copy all resource to dist folder
gulp.task('build', ['clean'], function () {
  gulp.run('images', 'htmls', 'vendor', 'scripts', 'styles');
});

gulp.task('default', function () {
  gulp.run('build');
});
