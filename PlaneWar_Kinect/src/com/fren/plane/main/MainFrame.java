package com.fren.plane.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import org.OpenNI.GeneralException;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.Point3D;
import org.OpenNI.StatusException;

import com.fren.config.ConfigKinect;
import com.fren.console.DebugPlaneMoveConsole;
import com.fren.kinect.model.OpenNi;
import com.fren.kinect.state.SessionState;
import com.primesense.NITE.HandEventArgs;
import com.primesense.NITE.HandPointContext;
import com.primesense.NITE.IdEventArgs;
import com.primesense.NITE.NullEventArgs;
import com.primesense.NITE.PointControl;
import com.primesense.NITE.PointEventArgs;
import com.primesense.NITE.SessionManager;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -5794242198248326611L;
	/**
	 * @param args
	 */
	public static final int FRAME_HEIGHT = 480;
	public static final int FRAME_WIDIH = 640;

	Image cacheImage = null;
	private Image background = null;
	private Image wait = null;
	private Image result = null;

	Controller controller = null;

	private OpenNi model;

	/**
	 * 游戏窗口显示构造函数
	 */
	public MainFrame() {
		// kinect
		try {
			ConfigKinect config = new ConfigKinect();
			model = config.config();
			this.setSessionEvents(model.getSessionMan());
			PointControl pointCtrl = this.initPointControl();
			model.getSessionMan().addListener(pointCtrl);
		} catch (GeneralException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// /////游戏窗口
		this.setSize(FRAME_WIDIH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		background = Toolkit.getDefaultToolkit().createImage(
				"res/background.png");
		wait = Toolkit.getDefaultToolkit().createImage("res/wait_t.png");
		result = Toolkit.getDefaultToolkit().createImage("res/result_t.png");

		// /////游戏窗口监听器
		this.addKeyListener(new MyKeyListener());
		this.addMouseMotionListener(new MyMouseListener());

		// 游戏控制器启动
		controller = new Controller();

		// //////游戏开始。。。
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		Thread mt = new Thread(new MainThread(this));
		mt.start();
	}

	/**
	 * 鼠标移动事件
	 * 
	 * @author Fren
	 * 
	 */
	class MyMouseListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			controller.onMouseListener(e);
		}
	}

	class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			// 把决定权交给控制器
			controller.onKeyListener(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(640, 480);
	}

	/**
	 * 所有的图形绘制都通过这里链接出去
	 */
	@Override
	public void paint(Graphics g) {
		if (controller.getGameState() == Controller.GAMESTATE_START)
			controller.onDraw(g);
		else if (controller.getGameState() == Controller.GAMESTATE_STOP) {

			g.drawImage(result, FRAME_WIDIH / 2 - 160, FRAME_HEIGHT / 2 - 115,
					null);
			g.drawString("" + controller.getScore(), FRAME_WIDIH / 2 - 10,
					FRAME_HEIGHT / 2);
		}
	}

	@Override
	public void update(Graphics g) {
		// super.update(g);
		if (cacheImage == null)
			cacheImage = this.createImage(FRAME_WIDIH, FRAME_HEIGHT);

		Graphics gCacheImage = cacheImage.getGraphics();
		gCacheImage
				.drawImage(background, 0, 0, FRAME_WIDIH, FRAME_HEIGHT, null);
		this.paint(gCacheImage);
		g.drawImage(cacheImage, 0, 0, null);
	}

	/**
	 * 游戏的计时器
	 * 
	 * @author public
	 * 
	 */
	class MainThread implements Runnable {

		MainFrame mf = null;

		public MainThread(MainFrame mf) {
			this.mf = mf;
		}

		@Override
		public void run() {
			boolean isRunning = true;
			while (isRunning) {
				try {
					// Thread.sleep(30);
					model.getContext().waitAnyUpdateAll();
					model.getSessionMan().update(model.getContext());

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (controller.getGameState() == Controller.GAMESTATE_START
						|| controller.getGameState() == Controller.GAMESTATE_STOP)
					mf.update(mf.getGraphics());
				else if (controller.getGameState() == Controller.GAMESTATE_PAUSE)
					mf.getGraphics().drawImage(wait, FRAME_WIDIH / 2 - 130,
							FRAME_HEIGHT / 2 - 65, null);

			}
			try {
				model.getContext().stopGeneratingAll();
			} catch (StatusException e) {
			}
			model.getContext().release();
		}
	}

	public static void main(String[] args) {
		// 主游戏开启
		MainFrame mf = new MainFrame();

	}

	/********* kinect controller *********/
	// create session callbacks
	private void setSessionEvents(SessionManager sessionMan) {
		try {
			// session start (S1)
			sessionMan.getSessionStartEvent().addObserver(
					new IObserver<PointEventArgs>() {
						public void update(
								IObservable<PointEventArgs> observable,
								PointEventArgs args) {
							System.out.println("Session started...");
							controller.setGameState(controller.GAMESTATE_START);
							model.setSessionState(SessionState.IN_SESSION);
						}
					});

			// session end (S2)
			sessionMan.getSessionEndEvent().addObserver(
					new IObserver<NullEventArgs>() {
						public void update(
								IObservable<NullEventArgs> observable,
								NullEventArgs args) {
							System.out.println("Session ended");
							controller.setGameState(controller.GAMESTATE_START);
							model.setSessionState(SessionState.NOT_IN_SESSION);
						}
					});
		} catch (StatusException e) {
			e.printStackTrace();
		}
	} // end of setSessionEvents()

	private PointControl initPointControl()
	// create 4 hand point listeners
	{
		PointControl pointCtrl = null;
		try {
			pointCtrl = new PointControl();

			// create new hand point, and hand trail (P1)
			pointCtrl.getPointCreateEvent().addObserver(
					new IObserver<HandEventArgs>() {
						public void update(
								IObservable<HandEventArgs> observable,
								HandEventArgs args) {
							model.setSessionState(SessionState.IN_SESSION);
							HandPointContext handContext = args.getHand();
							int id = handContext.getID();
							System.out.println("  Creating hand trail " + id);
							// handTrail.addPoint(handContext.getPosition());
							Point3D pt;
							try {
								pt = model.getDepthGen()
										.convertRealWorldToProjective(
												handContext.getPosition());

								//System.out.println(pt.getX() + "," + pt.getY());
								controller.onKinectListener(pt);
							} catch (StatusException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

			// hand point has moved; add to its trail (P2)
			pointCtrl.getPointUpdateEvent().addObserver(
					new IObserver<HandEventArgs>() {
						public void update(
								IObservable<HandEventArgs> observable,
								HandEventArgs args) {
							model.setSessionState(SessionState.IN_SESSION);
							HandPointContext handContext = args.getHand();
							int id = handContext.getID();
							// System.out.println("  Creating hand trail " +
							// id);
							Point3D pt;
							try {
								pt = model.getDepthGen()
										.convertRealWorldToProjective(
												handContext.getPosition());

								//System.out.println(pt.getX() + "," + pt.getY());
								controller.onKinectListener(pt);
							} catch (StatusException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// handTrail.addPoint(handContext.getPosition());
						}
					});

			// destroy hand point and its trail (P3)
			pointCtrl.getPointDestroyEvent().addObserver(
					new IObserver<IdEventArgs>() {
						public void update(IObservable<IdEventArgs> observable,
								IdEventArgs args) {
							int id = args.getId();
							System.out.println("  Deleting hand trail " + id);

						}
					});

			// no active hand point, which triggers refocusing (P4)
			pointCtrl.getNoPointsEvent().addObserver(
					new IObserver<NullEventArgs>() {
						public void update(
								IObservable<NullEventArgs> observable,
								NullEventArgs args) {
							if (model.getSessionState() != SessionState.NOT_IN_SESSION) {
								System.out
										.println("  Lost hand point, so refocusing");
								model.setSessionState(SessionState.QUICK_REFOCUS);
							}
						}
					});

		} catch (GeneralException e) {
			e.printStackTrace();
		}
		return pointCtrl;
	} // end of initPointControl()

}
