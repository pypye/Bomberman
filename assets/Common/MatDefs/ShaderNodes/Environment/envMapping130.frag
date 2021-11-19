
void main(){
        //@input vec3 refVec the reflection vector
    //@input samplerCube cubeMap the cube algorithms
    //@output vec4 color the output color

        color = textureLod(cubeMap, refVec, 0.0);
}