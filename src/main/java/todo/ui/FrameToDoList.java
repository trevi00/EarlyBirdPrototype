package todo.ui;

import bird.message.BirdMessageManager;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * [FrameToDoList]
 * - 사용자의 할 일 목록을 조회하고 완료 상태를 수정할 수 있는 화면
 */
public class FrameToDoList extends JFrame {

    private final ToDoService toDoService;
    private final String username;
    private final BirdMessageManager messageManager;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public FrameToDoList(ToDoService toDoService, String username, BirdMessageManager messageManager) {
        this.toDoService = toDoService;
        this.username = username;
        this.messageManager = messageManager;

        setTitle("할 일 목록 보기");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ✅ 테이블 컬럼 설정
        String[] columnNames = {"날짜", "제목", "내용", "완료 여부"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // 체크박스 컬럼 (완료 여부)
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return Boolean.class;
                return String.class;
            }

            // 완료 여부만 수정 가능
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ 저장 버튼
        JButton btnSave = new JButton("변경사항 저장");
        btnSave.addActionListener(e -> handleSave());
        add(btnSave, BorderLayout.SOUTH);

        loadToDos();

        setVisible(true);
    }

    private void loadToDos() {
        List<ToDo> list = toDoService.findByUsername(username);
        tableModel.setRowCount(0); // 초기화

        for (ToDo todo : list) {
            tableModel.addRow(new Object[]{
                    todo.getDate().toString(),
                    todo.getTitle(),
                    todo.getContent(),
                    todo.isDone()
            });
        }
    }

    private void handleSave() {
        int rowCount = tableModel.getRowCount();
        int changedCount = 0;

        for (int i = 0; i < rowCount; i++) {
            String dateStr = (String) tableModel.getValueAt(i, 0);
            boolean doneChecked = (Boolean) tableModel.getValueAt(i, 3);

            LocalDate date = LocalDate.parse(dateStr);
            ToDo original = toDoService.findByUsername(username).stream()
                    .filter(t -> t.getDate().equals(date))
                    .findFirst()
                    .orElse(null);

            if (original != null && !original.isDone() && doneChecked) {
                toDoService.markAsDone(username, date);
                changedCount++;
            }
        }

        if (changedCount > 0) {
            messageManager.say("✅ " + changedCount + "개의 할 일을 완료 처리했어요!");
        } else {
            messageManager.say("📝 변경된 할 일이 없습니다.");
        }

        loadToDos(); // 새로고침
    }
}
