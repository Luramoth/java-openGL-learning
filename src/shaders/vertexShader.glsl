#version 400 core

in vec3 position;// vertex positions for the model
in vec2 textureCoords;// texture UV's for the model
in vec3 normal;

out vec2 pass_textureCoords;// output to pass streight to the fragment shader
out vec3 surfaceNormal;
out vec3 toLightVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition;

void main(void){

    vec4 worldPosition = transformationMatrix * vec4(position.xyz,1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;// convert the current vertex positions to a 4D coordinate
    pass_textureCoords = textureCoords;// immidiately pass the UV coordinates to the fragment shader as we cant really use them

    surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
}