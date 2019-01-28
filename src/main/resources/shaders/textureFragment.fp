#version 430

in vec2 tex_coord;

out vec4 color;
uniform sampler2D texture_unit;

void main(void){
		
	color = vec4(1.0f,0.0f,1.0f,1.0f);//+texture(texture_unit, tex_coord);
	//gl_FragColor = vec4(1.0f,0.0f,1.0f,1.0f);
	
   
   
}
