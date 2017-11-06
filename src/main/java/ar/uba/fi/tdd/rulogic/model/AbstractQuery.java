package ar.uba.fi.tdd.rulogic.model;

import java.util.Map;

public class AbstractQuery extends Query {

    public AbstractQuery(String name, String[] params){ super(name, params);}

    private String[] replaceParams(Map<String,String> absParamsToInstancedParamsMap){
        String[] ownParams = this.getParams();
        String[] instancedParameters = new String[ownParams.length];

        for(int i = 0; i < ownParams.length; i++){
            if(absParamsToInstancedParamsMap.containsKey(ownParams[i]))
                instancedParameters[i] = absParamsToInstancedParamsMap.get(ownParams[i]);
            else
                instancedParameters[i] = ownParams[i];
        }

        return instancedParameters;

    }

    public Query getInstancedQuery(Map<String,String> absParamsToInstancedParamsMap){
        return new Query(this.getName(), this.replaceParams(absParamsToInstancedParamsMap));
    }

}
