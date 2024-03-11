package cn.elevendev.marquee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.AnimRes;
import androidx.annotation.FontRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import cn.elevendev.marquee.Utils.Util;


public class MarqueeView<T> extends ViewFlipper {

    private int interval = 2000;
    private boolean hasSetAnimDuration = false;
    private int animDuration = 2000;
    private int textSize = 14;
    private int textColor = 0xff1a1a1a;
    private boolean singleLine = false;

    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;

    private int direction = DIRECTION_BOTTOM_TO_TOP;
    private static final int DIRECTION_BOTTOM_TO_TOP = 0;
    private static final int DIRECTION_TOP_TO_BOTTOM = 1;
    private static final int DIRECTION_RIGHT_TO_LEFT = 2;
    private static final int DIRECTION_LEFT_TO_RIGHT = 3;

    private Typeface typeface;

    @AnimRes
    private int inAnimResId = R.anim.bottom_in;
    @AnimRes
    private int outAnimResId = R.anim.top_out;

    private int position;
    private List<T> messages = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);

        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_eInterval, interval);
        hasSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_eAnimDuration);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_eAnimDuration, animDuration);
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_eSingleLine, false);
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_eTextSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_eTextSize, textSize);
            textSize = Util.px2sp(context, textSize);
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_eTextColor, textColor);
        @FontRes int fontRes = typedArray.getResourceId(R.styleable.MarqueeViewStyle_eFont, 0);
        if (fontRes != 0) {
            typeface = ResourcesCompat.getFont(context, fontRes);
        }
        int gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_eGravity, GRAVITY_LEFT);
        switch (gravityType) {
            case GRAVITY_LEFT:
                gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }

        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_eDirection)) {
            direction = typedArray.getInt(R.styleable.MarqueeViewStyle_eDirection, direction);
            switch (direction) {
                case DIRECTION_BOTTOM_TO_TOP:
                    inAnimResId = R.anim.bottom_in;
                    outAnimResId = R.anim.top_out;
                    break;
                case DIRECTION_TOP_TO_BOTTOM:
                    inAnimResId = R.anim.top_in;
                    outAnimResId = R.anim.bottom_out;
                    break;
                case DIRECTION_RIGHT_TO_LEFT:
                    inAnimResId = R.anim.right_in;
                    outAnimResId = R.anim.left_out;
                    break;
                case DIRECTION_LEFT_TO_RIGHT:
                    inAnimResId = R.anim.left_in;
                    outAnimResId = R.anim.right_out;
                    break;
            }
        } else {
            inAnimResId = R.anim.bottom_in;
            outAnimResId = R.anim.top_out;
        }

        typedArray.recycle();
        setFlipInterval(interval);
    }

    /**
     * 滚动修改方向
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
        switch (direction) {
            case DIRECTION_BOTTOM_TO_TOP:
                inAnimResId = R.anim.bottom_in;
                outAnimResId = R.anim.top_out;
                break;
            case DIRECTION_TOP_TO_BOTTOM:
                inAnimResId = R.anim.top_in;
                outAnimResId = R.anim.bottom_out;
                break;
            case DIRECTION_RIGHT_TO_LEFT:
                inAnimResId = R.anim.right_in;
                outAnimResId = R.anim.left_out;
                break;
            case DIRECTION_LEFT_TO_RIGHT:
                inAnimResId = R.anim.left_in;
                outAnimResId = R.anim.right_out;
                break;
        }
    }

    /**
     * 设置字体大小
     * @param size
     */
    public void setTextSize(int size) {
        this.textSize = size;
    }

    /**
     * 设置字体颜色
     * @param color
     */
    public void setTextColor(int color) {
        this.textColor = color;
    }
    public void setTextColor(String colorStr) {
        this.textColor = Color.parseColor(colorStr);
    }

    /**
     * 设置滚动时间
     * @param interval
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 设置动画持续时间
     * @param animDuration
     */
    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    /**
     * 设置是否单行显示
     * @param singleLine
     */
    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    /**
     * 设置文本对齐方式
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }



    /**
     * 根据字符串，启动翻页公告
     * @param message 字符串
     */
    public void startWithText(String message) {
        startWithText(message, inAnimResId, outAnimResId);
    }

    /**
     * 根据字符串，启动翻页公告
     * @param message      字符串
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    public void startWithText(final String message, final @AnimRes int inAnimResId,
                              final @AnimRes int outAnimResID) {
        if (TextUtils.isEmpty(message)) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                startWithFixedWidth(message, inAnimResId, outAnimResID);
            }
        });
    }

    /**
     * 根据字符串和宽度，启动翻页公告
     * @param message 字符串
     */
    private void startWithFixedWidth(String message, @AnimRes int inAnimResId,
                                     @AnimRes int outAnimResID) {
        int messageLength = message.length();
        int width = Util.px2dip(getContext(), getWidth());
        if (width == 0) {
            throw new RuntimeException("Please set the width of MarqueeView !");
        }
        int limit = width / textSize;
        List list = new ArrayList();

        if (messageLength <= limit) {
            list.add(message);
        } else {
            int size = messageLength / limit + (messageLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= messageLength ? messageLength : (i + 1) * limit);
                list.add(message.substring(startIndex, endIndex));
            }
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.clear();
        messages.addAll(list);
        postStart(inAnimResId, outAnimResID);
    }

    /**
     * 根据字符串列表，启动翻页公告
     * @param messages 字符串列表
     */
    public void startWithList(List<T> messages) {
        startWithList(messages, inAnimResId, outAnimResId);
    }

    /**
     * 根据字符串列表，启动翻页公告
     * @param messages     字符串列表
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    public void startWithList(List<T> messages, @AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        if (Util.isEmpty(messages)) return;
        setMessages(messages);
        postStart(inAnimResId, outAnimResID);
    }

    private void postStart(final @AnimRes int inAnimResId, final @AnimRes int outAnimResID) {
        post(new Runnable() {
            @Override
            public void run() {
                start(inAnimResId, outAnimResID);
            }
        });
    }

    private boolean isAnimStart = false;

    private void start(final @AnimRes int inAnimResId, final @AnimRes int outAnimResID) {
        removeAllViews();
        clearAnimation();
        // 检测数据源
        if (messages == null || messages.isEmpty()) {
            throw new RuntimeException("The messages cannot be empty!");
        }
        position = 0;
        addView(createTextView(messages.get(position)));

        if (messages.size() > 1) {
            setInAndOutAnimation(inAnimResId, outAnimResID);
            startFlipping();
        }

        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isAnimStart) {
                        animation.cancel();
                    }
                    isAnimStart = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    position++;
                    if (position >= messages.size()) {
                        position = 0;
                    }
                    View view = createTextView(messages.get(position));
                    if (view.getParent() == null) {
                        addView(view);
                    }
                    isAnimStart = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    private TextView createTextView(T marqueeItem) {
        TextView textView = (TextView) getChildAt((getDisplayedChild() + 1) % 3);
        if (textView == null) {
            textView = new TextView(getContext());
            textView.setGravity(gravity | Gravity.CENTER_VERTICAL);
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            textView.setIncludeFontPadding(true);
            textView.setSingleLine(singleLine);
            if (singleLine) {
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
            }
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getPosition(), (TextView) v);
                    }
                }
            });
        }
        CharSequence message = "";
        if (marqueeItem instanceof CharSequence) {
            message = (CharSequence) marqueeItem;
        } else if (marqueeItem instanceof MarqueeItem) {
            message = ((MarqueeItem) marqueeItem).marqueeMessage();
        }
        textView.setText(message);
        textView.setTag(position);
        return textView;
    }

    public int getPosition() {
        return (int) getCurrentView().getTag();
    }

    public List<T> getMessages() {
        return messages;
    }

    public void setMessages(List<T> messages) {
        this.messages = messages;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TextView textView);
    }

    /**
     * 设置进入动画和离开动画
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        if (hasSetAnimDuration) inAnim.setDuration(animDuration);
        setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        if (hasSetAnimDuration) outAnim.setDuration(animDuration);
        setOutAnimation(outAnim);
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
}
