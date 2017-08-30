module.exports = function (grunt) {

grunt.initConfig({
	uncss: {
		dist: {
			options: {
			  media: [
						'(min-width: 320px)',
						'(min-width: 768px)',
						'(min-width: 992px)',
						'(min-width: 1200px)'
					 ],
			  ignoreSheets: [/fonts.googleapis.com/, /maxcdn.bootstrapcdn.com/, /ajax.googleapis.com/],
			  ignore: [
						/\.has-/,
						/\.glyphicon/,
						/\.tooltip/,
						/\.dropdown/,
						/\.open/,
						/\.alert/,
						/\#lift__noticesContainer/,
						/\.collapse/,
						/\.fade/,
						/\.collapsing/,
						/\.collapsed/,
						/\.show-notice/,
						/\.close/
					]
			},
			files: [{ 
				  nonull: true,
				  src: [
						'http://localhost:8080',
						'http://localhost:8080/products/title_plants',
						'http://localhost:8080/our-wedding',
						'http://localhost:8080/accommodations',
						'http://localhost:8080/registry',
						'http://localhost:8080/404'
					  ],
				  dest: 'uncss.css'
			  }
			]
		}
	},
	cssmin: {
	  dist: {
		  files: {
		    'styles.css': ['uncss.css']
		  }
	  }
	}
});

// Load the plugins
grunt.loadNpmTasks('grunt-uncss');
grunt.loadNpmTasks('grunt-contrib-cssmin');

// Default tasks.
grunt.registerTask('default', ['uncss', 'cssmin']);

};