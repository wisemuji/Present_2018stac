package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.sdk.newtoneapi.SpeechRecognizerActivity;

import java.util.ArrayList;
import java.util.List;

import s2017s25.kr.hs.mirim.present_2018stac.R;

public class VoiceRecoActivity extends SpeechRecognizerActivity {
    public static String EXTRA_KEY_RESULT_ARRAY = "result_array"; // 결과값 목록. ArrayList<String>
    public static String EXTRA_KEY_MARKED = "marked"; // 첫번째 값의 신뢰도가 현저하게 높은 경우 true. 아니면 false. Boolean
    public static String EXTRA_KEY_ERROR_CODE = "error_code"; // 에러가 발생했을 때 코드값. 코드값은 SpeechRecognizerClient를 참조. Integer
    public static String EXTRA_KEY_ERROR_MESSAGE = "error_msg"; // 에러 메시지. String

    protected void putStringFromId(RES_STRINGS key, int id) {
        putString(key, getResources().getString(id));
    }

    // 이 함수를 꼭 상속받아서 구현해야 한다.
    // 내부에서 사용하는 리소스 id를 모두 등록해준다.
    // 일부 표시하지 않을 view의 경우에는 layout에서 제거하지 말고 visibility를 hidden으로 설정하는 것을 권장한다.
    @Override
    protected void onResourcesWillInitialize() {
        // strings
        putStringFromId(RES_STRINGS.PLEASE_SPEAK, R.string.com_kakao_sdk_asr_voice_search_please_speak);
        putStringFromId(RES_STRINGS.THIS_DEVICE_MAY_BE_NOT_SUPPORTED, R.string.com_kakao_sdk_asr_voice_search_warn_not_support_device);
        putStringFromId(RES_STRINGS.SELECT_THAT_YOU_SPEAK_FROM_SUGGEST, R.string.com_kakao_sdk_asr_voice_search_suggest_guide);
        putStringFromId(RES_STRINGS.RETRY_SPEAKING_WITH_TOUCH_BUTTON, R.string.com_kakao_sdk_asr_voice_search_tip_retry_button);
        putStringFromId(RES_STRINGS.TOUCH_BUTTON_TO_USE_RESULT_INSTANTLY, R.string.com_kakao_sdk_asr_voice_search_tip_instant_search);
        putStringFromId(RES_STRINGS.SORRY_RECOGNITION_FAILED, R.string.com_kakao_sdk_asr_voice_search_recognition_failed);
        putStringFromId(RES_STRINGS.RETRY_AFTER_A_WHILE, R.string.com_kakao_sdk_asr_voice_search_tip_retry_after_a_while);
        putStringFromId(RES_STRINGS.PLEASE_SPEAK_NATURAL, R.string.com_kakao_sdk_asr_voice_search_tip_natural);
        putStringFromId(RES_STRINGS.NETWORK_IS_UNSTABLE, R.string.com_kakao_sdk_asr_voice_search_error_network);

        // view id
        // main
        putViewId(RES_VIEWID.VIEW_VOICE_RECO_ACTIVITY, R.layout.com_kakao_sdk_asr_activity_voice_reco);
        putViewId(RES_VIEWID.LAYOUT_MAIN, R.id.com_kakao_sdk_asr_main_layout);
        putViewId(RES_VIEWID.LISTVIEW_SUGGEST_PORTRAIT, R.id.com_kakao_sdk_asr_list_suggest_portrait);
        putViewId(RES_VIEWID.LISTVIEW_SUGGEST_LANDSCAPE, R.id.com_kakao_sdk_asr_list_suggest_landscape);
        putViewId(RES_VIEWID.BUTTON_VOICE_START, R.id.com_kakao_sdk_asr_button_voice);
        putViewId(RES_VIEWID.BUTTON_RETRY, R.id.com_kakao_sdk_asr_button_retry);
        putViewId(RES_VIEWID.PROGRESSBAR_ANALYSIS, R.id.com_kakao_sdk_asr_progress_analysis);
        putViewId(RES_VIEWID.CANVAS_SURFACE_VIEW, R.id.com_kakao_sdk_asr_progress_view);
        putViewId(RES_VIEWID.TEXTVIEW_MESSAGE, R.id.com_kakao_sdk_asr_text_message);
        putViewId(RES_VIEWID.TEXTVIEW_INTERMEDIATE_MESSAGE, R.id.com_kakao_sdk_asr_text_intermediate_message);
        putViewId(RES_VIEWID.TEXTVIEW_SUGGEST, R.id.com_kakao_sdk_asr_text_suggest);
        putViewId(RES_VIEWID.TEXTVIEW_TIP, R.id.com_kakao_sdk_asr_text_tip);

        // top
        putViewId(RES_VIEWID.LAYOUT_TOP, R.id.com_kakao_sdk_asr_top_layout);
        putViewId(RES_VIEWID.BUTTON_KAKAO, R.id.com_kakao_sdk_asr_button_kakao);
        putViewId(RES_VIEWID.BUTTON_TOP_CLOSE, R.id.com_kakao_sdk_asr_button_top_close);

        // suggest list
        putViewId(RES_VIEWID.LAYOUT_SUGGEST_LANDSCAPE, R.id.com_kakao_sdk_asr_layout_suggest_landscape);
        putViewId(RES_VIEWID.VIEW_SUGGEST_LIST_ITEM, R.layout.com_kakao_sdk_asr_view_suggest_item);
        putViewId(RES_VIEWID.TEXTVIEW_SUGGEST, R.id.com_kakao_sdk_asr_text_suggest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // activity가 표시될 때의 transition 효과 설정
        overridePendingTransition(R.anim.com_kakao_sdk_asr_grow_height_from_top, android.R.anim.fade_in);

        // isValidResourceMappings()을 호출하면 리소스 및 view id 설정이 안된 것이 있는지 체크할 수 있다.
        boolean resourcePassed = isValidResourceMappings();
        Log.i("VoiceRecoActivity", "resource pass : " + resourcePassed);

        if (!resourcePassed) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        // 물방울 애니메이션에 사용되는 drawable의 id 목록
        int[] bitmaps = {
                R.drawable.com_kakao_sdk_asr_ani_circle_blue,
                R.drawable.com_kakao_sdk_asr_ani_circle_green,
                R.drawable.com_kakao_sdk_asr_ani_circle_yellow,
                R.drawable.com_kakao_sdk_asr_ani_circle_red};

        setRendererRandomBitmapIds(bitmaps);
    }

    @Override
    public void finish() {
        super.finish();

        // activity가 사라질 때 transition 효과 지정
        overridePendingTransition(android.R.anim.fade_out, R.anim.com_kakao_sdk_asr_shrink_height_from_bottom);
    }

    @Override
    protected void onRecognitionSuccess(List<String> result, boolean marked) {
        // result는 선택된 결과 목록이 담겨있다.
        // 첫번째 값의 신뢰도가 낮아 후보 단어를 선택하는 과정을 거쳤을 경우에는 그 때 선택된 값이 가장 처음으로 오게 된다.
        // 첫번째 값의 신뢰도가 현저하게 높았거나, 이용자가 선택을 했을 경우에는 marked 값은 true가 된다. 이 이외에는 false가 된다.
        Intent intent = new Intent().
                putStringArrayListExtra(EXTRA_KEY_RESULT_ARRAY, new ArrayList<String>(result)).
                putExtra(EXTRA_KEY_MARKED, marked);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onRecognitionFailed(int errorCode, String errorMsg) {
        Intent intent = new Intent().
                putExtra(EXTRA_KEY_ERROR_CODE, errorCode).
                putExtra(EXTRA_KEY_ERROR_MESSAGE, errorMsg);

        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
