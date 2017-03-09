package koryphe.function.transform;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MapTransformTest {
    @Test
    public void testMapTransform() {
        int inA = 1;
        int inB = 2;
        int outA = 2;
        int outB = 4;

        Map<String, Integer> inputMap = new HashMap<>();
        inputMap.put("a", inA);
        inputMap.put("b", inB);

        Transformer<Integer, Integer> transformer = mock(Transformer.class);
        given(transformer.execute(inA)).willReturn(outA);
        given(transformer.execute(inB)).willReturn(outB);

        TransformByKey<String, Integer, Integer> transformByKey = new TransformByKey<>();
        transformByKey.setTransformer(transformer);

        Map<String, Integer> outputMap = transformByKey.execute(inputMap);

        assertEquals(outA, (int) outputMap.get("a"));
        assertEquals(outB, (int) outputMap.get("b"));
    }
}