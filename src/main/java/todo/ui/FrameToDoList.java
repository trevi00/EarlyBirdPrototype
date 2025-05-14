package todo.ui;

import bird.message.BirdMessageManager;
import todo.model.ToDo;
import todo.service.ToDoService;
import user.session.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * [FrameToDoList]
 * - 오늘까지 작성한 할 일 목록을 조회하고,
 * - 선택 항목을 삭제하거나 완료로 표시할 수 있는 화면
 */
public class FrameToDoList extends JFrame {

    private final ToDoService toDoService;
    private final BirdMessageManager messageManager;
    private final DefaultListModel<ToDo> listModel;

    public FrameToDoList(ToDoService toDoService, BirdMessageManager messageManager) {
        this.toDoService = toDoService;
        this.messageManager = messageManager;
        this.listModel = new DefaultListModel<>();

        setTitle("할 일 목록");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 리스트 구성
        JList<ToDo> toDoJList = new JList<>(listModel);
        toDoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoJList.setCellRenderer(new ToDoListCellRenderer());

        // 삭제 버튼
        JButton btnDelete = new JButton("🗑 삭제하기");
        btnDelete.addActionListener(e -> {
            ToDo selected = toDoJList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "삭제할 항목을 선택해주세요.");
                return;
            }
            toDoService.deleteToDo(selected.getUsername(), selected.getDate());
            listModel.removeElement(selected);
            JOptionPane.showMessageDialog(this, "삭제 완료!");
            messageManager.say("할 일을 잘 정리했어요!");
        });

        // ✅ 완료 표시 버튼
        JButton btnDone = new JButton("✅ 완료 표시");
        btnDone.addActionListener(e -> {
            ToDo selected = toDoJList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "완료할 항목을 선택해주세요.");
                return;
            }

            toDoService.markAsDone(selected.getUsername(), selected.getDate());
            JOptionPane.showMessageDialog(this, "✅ 완료 처리되었습니다!");
            messageManager.say("할 일을 마무리했군요! 잘했어요!");
            reload(); // 목록 갱신
        });

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnDone);

        loadData();

        add(new JScrollPane(toDoJList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        String username = SessionManager.getCurrentUser().getUsername();
        List<ToDo> toDos = toDoService.getAllToDos(username);
        listModel.clear();
        toDos.forEach(listModel::addElement);
    }

    private void reload() {
        loadData();
    }

    /**
     * 리스트에 출력할 형식 지정
     */
    private static class ToDoListCellRenderer extends JLabel implements ListCellRenderer<ToDo> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public Component getListCellRendererComponent(JList<? extends ToDo> list, ToDo value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText("📅 " + value.getDate().format(formatter) + " - "
                    + (value.isDone() ? "[완료] " : "") + value.getTitle());

            setOpaque(true);
            setBackground(isSelected ? new Color(200, 230, 255) : Color.WHITE);
            setForeground(Color.DARK_GRAY);
            setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }
}