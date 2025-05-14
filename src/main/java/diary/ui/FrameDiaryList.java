package diary.ui;

import diary.handler.DiaryHandler;
import diary.model.Diary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * [FrameDiaryList]
 * - 로그인한 사용자의 전체 일기 목록을 테이블로 표시
 * - 일기 선택 후 수정 또는 삭제 가능
 * - DiaryHandler를 통해 서비스 계층과 연결됨
 */
public class FrameDiaryList extends JFrame {

    private final DiaryHandler diaryHandler;
    private JTable diaryTable;
    private DefaultTableModel tableModel;

    /**
     * 생성자
     * @param diaryHandler 일기 목록 조회 및 삭제 처리 핸들러
     */
    public FrameDiaryList(DiaryHandler diaryHandler) {
        this.diaryHandler = diaryHandler;

        setTitle("나의 일기 목록");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadDiaryList();

        setVisible(true);
    }

    /**
     * UI 구성: 테이블 + 버튼
     */
    private void initUI() {
        // 테이블 초기화
        String[] columns = {"날짜", "제목"};
        tableModel = new DefaultTableModel(columns, 0);
        diaryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(diaryTable);
        add(scrollPane, BorderLayout.CENTER);

        // 버튼 영역
        JPanel buttonPanel = new JPanel();
        JButton btnEdit = new JButton("수정");
        JButton btnDelete = new JButton("삭제");

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        // 수정 버튼
        btnEdit.addActionListener(e -> {
            int row = diaryTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "수정할 일기를 선택해주세요.");
                return;
            }

            String dateStr = (String) diaryTable.getValueAt(row, 0);
            LocalDate date = LocalDate.parse(dateStr);
            new FrameDiaryEdit(diaryHandler, date);
        });

        // 삭제 버튼
        btnDelete.addActionListener(e -> {
            int row = diaryTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "삭제할 일기를 선택해주세요.");
                return;
            }

            String dateStr = (String) diaryTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                diaryHandler.deleteDiary(LocalDate.parse(dateStr));
                loadDiaryList(); // 삭제 후 목록 새로고침
            }
        });
    }

    /**
     * 사용자 일기 목록을 테이블에 로딩
     */
    private void loadDiaryList() {
        tableModel.setRowCount(0); // 테이블 초기화
        List<Diary> diaryList = diaryHandler.getDiaryList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Diary diary : diaryList) {
            tableModel.addRow(new Object[]{
                    diary.getDate().format(formatter),
                    diary.getTitle()
            });
        }
    }
}